@file:OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)

package com.plcoding.feature.chat.data.network

import com.plcoding.core.data.tools.BASE_URL_WSS
import com.plcoding.core.domain.logger.Logger
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.data.BuildKonfig
import com.plcoding.feature.chat.data.model.WebSocketMessageDto
import com.plcoding.feature.chat.domain.model.ConnectionError
import com.plcoding.feature.chat.domain.model.ConnectionState
import com.plcoding.feature.chat.domain.network.ConnectionErrorHandler
import com.plcoding.feature.chat.domain.observer.AppConnectivityObserver
import com.plcoding.feature.chat.domain.observer.AppLifecycleObserver
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

class KtorWebSocketConnector(
  private val json: Json,
  private val logger: Logger,
  private val httpClient: HttpClient,
  private val coroutineScope: CoroutineScope,
  private val appLifecycleObserver: AppLifecycleObserver,
  private val appConnectivityObserver: AppConnectivityObserver,
  private val connectionRetryHandler: ConnectionRetryHandler,
  private val connectionErrorHandler: ConnectionErrorHandler,
  private val preferencesRepository: PreferencesRepository,
) {

  private var webSocketSession: WebSocketSession? = null

  private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
  val connectionState = _connectionState.asStateFlow()

  private val isInForeground = appLifecycleObserver
    .isInForeground
    .debounce(1.seconds)
    .stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000L), false)

  private val isConnected = appConnectivityObserver
    .isConnected
    .debounce(1.seconds)
    .stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000L), false)

  val webSocketMessagesDto = combine(
    preferencesRepository.observeAuthInfo(),
    appLifecycleObserver.isInForeground,
    appConnectivityObserver.isConnected,
  ) { authInfo, isInForeground, isConnected ->
    when {
      authInfo == null -> {
        closeWebSocketSession("Unauthorized", ConnectionState.DISCONNECTED)
        null
      }
      !isInForeground -> {
        closeWebSocketSession("App is in Background", ConnectionState.DISCONNECTED)
        null
      }
      !isConnected -> {
        closeWebSocketSession("Internet is gone", ConnectionState.ERROR_NETWORK)
        null
      }
      else -> {
        logger.debug("Trying to start WebSocket connection...")

        if (_connectionState.value !in listOf(
            ConnectionState.CONNECTING,
            ConnectionState.CONNECTED
          )
        ) {
          _connectionState.value = ConnectionState.CONNECTING
        }

        authInfo
      }
    }
  }.flatMapLatest { authInfo ->
    if (authInfo == null) {
      emptyFlow()
    } else {
      createWebSocketFlow(authInfo.accessToken)
        // Catch block for transforming Exception (platform compatibility)
        .catch {
          closeWebSocketSession(
            "Not platform compatible Error is caught",
            ConnectionState.DISCONNECTED,
          )
          throw connectionErrorHandler.transformException(it)
        }
        .retryWhen { cause, attempt ->
          logger.debug("Retry attempt = $attempt due to error = $cause")

          val shouldRetry = connectionRetryHandler.shouldRetry(cause)

          if (shouldRetry) {
            _connectionState.value = ConnectionState.CONNECTING
            connectionRetryHandler.delayExponentially(attempt)
          }

          shouldRetry
        }
        // Uncaught exception occurred
        .catch {
          logger.debug("Unhandled exception occurred = $it")
          _connectionState.value = connectionErrorHandler.getConnectionState(it)
        }
    }
  }

  private fun createWebSocketFlow(accessToken: String): Flow<WebSocketMessageDto> {
    return callbackFlow {
      _connectionState.value = ConnectionState.CONNECTING

      webSocketSession = httpClient.webSocketSession(
        urlString = "$BASE_URL_WSS/chat"
      ) {
        header("Authorization", "Barer $accessToken")
        header("x-api-key", BuildKonfig.API_KEY)
      }

      webSocketSession?.let { session ->
        _connectionState.value = ConnectionState.CONNECTED

        session
          .incoming
          .consumeAsFlow<Frame>()
          .buffer(100)
          .collect { frame ->
            when (frame) {
              is Frame.Text -> {
                val message = frame.readText()
                logger.debug("Received raw text frame: $message")
                val webSocketMessageDto = json.decodeFromString<WebSocketMessageDto>(message)
                send(webSocketMessageDto)
              }
              is Frame.Ping -> {
                logger.debug("Received ping from server. Sending pong...")
                session.send(Frame.Pong(data = frame.data))
              }
              else -> Unit
            }
          }
      } ?: throw Exception("Failed to create WebSocketSession...")

      awaitClose {
        launch(NonCancellable) {
          closeWebSocketSession("Coroutine is cancelled, ", ConnectionState.DISCONNECTED)
        }
      }
    }
  }

  private suspend fun closeWebSocketSession(
    log: String,
    connectionState: ConnectionState,
  ) {
    logger.debug("$log, closing WebSocket connection...")
    _connectionState.value = ConnectionState.DISCONNECTED
    webSocketSession?.close()
    webSocketSession = null
  }

  suspend fun sendMessage(message: String): Empty<ConnectionError> {
    val connectionState = _connectionState.value

    if (webSocketSession == null || connectionState != ConnectionState.CONNECTED) {
      return Result.Failure(ConnectionError.NOT_CONNECTED)
    }

    return try {
      webSocketSession?.send(message)
      Result.Success(Unit)
    } catch (e: Exception) {
      currentCoroutineContext().ensureActive()
      Result.Failure(ConnectionError.MESSAGE_SEND_FAILED)
    }
  }
}