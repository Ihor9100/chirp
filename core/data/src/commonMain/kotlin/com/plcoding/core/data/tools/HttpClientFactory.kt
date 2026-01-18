package com.plcoding.core.data.tools

import com.plcoding.core.data.BuildKonfig
import com.plcoding.core.data.mapper.AuthInfoMapper
import com.plcoding.core.data.model.AuthInfoAm
import com.plcoding.core.data.model.RefreshRequestAm
import com.plcoding.core.data.repository.local.PreferencesLocalDataRepository
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import com.plcoding.core.domain.logger.Logger as ChirpLogger
import io.ktor.client.plugins.logging.Logger as KtorLogger

class HttpClientFactory(
  private val json: Json,
  private val chirpLogger: ChirpLogger,
  private val preferencesLocalDataRepository: PreferencesLocalDataRepository,
  private val authInfoMapper: AuthInfoMapper,
  private val httpClientEngine: HttpClientEngine,
) {

  fun create(): HttpClient {
    return HttpClient(httpClientEngine) {
      install(ContentNegotiation) {
        json(json)
      }
      install(HttpTimeout) {
        requestTimeoutMillis = 20_000L
        socketTimeoutMillis = 20_000L
      }
      install(Logging) {
        logger = object : KtorLogger {
          override fun log(message: String) {
            chirpLogger.debug(message)
          }
        }
        level = LogLevel.ALL
      }
      install(WebSockets) {
        pingIntervalMillis = 20_000L
      }
      defaultRequest {
        header("x-api-key", BuildKonfig.API_KEY)
        contentType(ContentType.Application.Json)
      }
      install(Auth) {
        bearer {
          loadTokens {
            getAuthInfo()?.run {
              BearerTokens(
                accessToken = accessToken,
                refreshToken = refreshToken
              )
            }
          }
          refreshTokens {
            if (response.request.url.encodedPath.contains("auth/")) {
              return@refreshTokens null
            }

            val authInfo = getAuthInfo()

            if (authInfo?.refreshToken.isNullOrBlank()) {
              preferencesLocalDataRepository.saveAuthInfo(null)
              return@refreshTokens null
            }

            var bearerTokens: BearerTokens? = null

            client.post<RefreshRequestAm, AuthInfoAm>(
              route = "/auth/refresh",
              request = RefreshRequestAm(authInfo.refreshToken),
              builder = { markAsRefreshTokenRequest() }
            ).onSuccess {
              preferencesLocalDataRepository.saveAuthInfo(authInfoMapper.map(it, Unit))
              bearerTokens = BearerTokens(it.accessToken, it.refreshToken)
            }.onFailure {
              preferencesLocalDataRepository.saveAuthInfo(null)
            }

            bearerTokens
          }
        }
      }
    }
  }

  private suspend fun getAuthInfo(): AuthInfo? {
    return preferencesLocalDataRepository
      .observeAuthInfo()
      .firstOrNull()
  }
}