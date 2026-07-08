package com.plcoding.feature.chat.data.handler

import com.plcoding.chat.domain.models.ConnectionState
import com.plcoding.feature.chat.domain.handler.ConnectionErrorHandler
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.websocket.WebSocketException
import kotlinx.io.EOFException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

class AndroidConnectionErrorHandler: ConnectionErrorHandler {

  override fun getConnectionState(throwable: Throwable): ConnectionState {
    return when (throwable) {
      is ClientRequestException,
      is WebSocketException,
      is SocketException,
      is SocketTimeoutException,
      is UnknownHostException,
      is SSLException,
      is EOFException -> ConnectionState.ERROR_NETWORK
      else -> ConnectionState.ERROR_UNKNOWN
    }
  }

  override fun transformException(throwable: Throwable): Throwable {
    return throwable
  }

  override fun isRetriableError(throwable: Throwable): Boolean {
    return when(throwable) {
      is SocketTimeoutException,
      is WebSocketException,
      is SocketException,
      is EOFException -> true
      else -> false
    }
  }
}