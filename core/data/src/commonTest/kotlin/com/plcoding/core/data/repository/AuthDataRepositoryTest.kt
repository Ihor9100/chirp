package com.plcoding.core.data.repository

import com.plcoding.core.data.model.RegisterRequestDto
import com.plcoding.core.domain.result.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.header
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.time.Clock

class AuthDataRepositoryTest {

  @Test
  fun `register call returns success`() = runTest {
    var httpRequestData: HttpRequestData? = null
    val requestTime = Clock.System.now()
    val registerRequestDto = RegisterRequestDto(
      username = "test",
      email = "test@example.com",
      password = "Naruto*19890702"
    )
    val mockEngine = MockEngine {
      httpRequestData = it
      respond(content = "")
    }
    val httpClient = HttpClient(mockEngine) {
      install(ContentNegotiation) {
        json()
      }
      defaultRequest {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
      }
    }
    val authDataRepository = AuthDataRepository(httpClient)

    val response = authDataRepository.register(
      username = registerRequestDto.username,
      email = registerRequestDto.email,
      password = registerRequestDto.password,
    )

    assertEquals("/api/auth/register", httpRequestData?.url?.encodedPath)
    assertEquals(HttpMethod.Post, httpRequestData?.method)
    val body = httpRequestData?.body as? TextContent
    val bodyText = body?.text
    assertEquals(registerRequestDto, bodyText?.let { Json.decodeFromString<RegisterRequestDto>(it) })
    assertIs<Result.Success<Unit>>(response)
  }
}