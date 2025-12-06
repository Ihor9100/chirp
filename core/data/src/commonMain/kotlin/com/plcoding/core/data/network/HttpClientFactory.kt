package com.plcoding.core.data.network

import com.plcoding.core.data.BuildKonfig
import com.plcoding.core.domain.logger.ChirpLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
  private val json: Json,
  private val chirpLogger: ChirpLogger,
) {

  fun create(httpClientEngine: HttpClientEngine): HttpClient {
    return HttpClient(httpClientEngine) {
      install(ContentNegotiation) {
        json(json)
      }
      install(HttpTimeout) {
        requestTimeoutMillis = 20_000L
        socketTimeoutMillis = 20_000L
      }
      install(Logging) {
        logger = object : Logger {
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
    }
  }
}