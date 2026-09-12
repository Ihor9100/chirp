package com.plcoding.core.data.repository

import com.plcoding.core.data.model.RegisterRequestDto
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.header
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AuthDataRepositoryTest {

  private fun getAuthDataRepository(mockEngine: MockEngine): AuthDataRepository {
    val httpClient = HttpClient(mockEngine) {
      install(ContentNegotiation) {
        json()
      }
      defaultRequest {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
      }
    }
    return AuthDataRepository(httpClient)
  }

  private val registerRequestDto = RegisterRequestDto(
    username = "test",
    email = "test@example.com",
    password = "Naruto*19890702"
  )

  @Test
  fun `register sends post request with credentials`() = runTest {
    var httpRequestData: HttpRequestData? = null
    val mockEngine = MockEngine {
      httpRequestData = it
      respondOk()
    }

    getAuthDataRepository(mockEngine).register(
      username = registerRequestDto.username,
      email = registerRequestDto.email,
      password = registerRequestDto.password,
    )

    val bodyText = (httpRequestData?.body as? TextContent)?.text
    assertEquals("/api/auth/register", httpRequestData?.url?.encodedPath)
    assertEquals(HttpMethod.Post, httpRequestData?.method)
    assertEquals(registerRequestDto, bodyText?.let(Json::decodeFromString))
  }

  @Test
  fun `409 conflict register response returns conflict error`() = runTest {
    val mockEngine = MockEngine {
      respondError(HttpStatusCode.Conflict)
    }

    val result = getAuthDataRepository(mockEngine).register(
      username = registerRequestDto.username,
      email = registerRequestDto.email,
      password = registerRequestDto.password,
    )

    assertIs<Result.Failure<DataError.Remote>>(result)
    assertEquals(DataError.Remote.CONFLICT, result.error)
  }


  @Test
  fun `200 Ok successful register response returns success`() = runTest {
    val mockEngine = MockEngine {
      respondOk()
    }

    val result = getAuthDataRepository(mockEngine).register(
      username = registerRequestDto.username,
      email = registerRequestDto.email,
      password = registerRequestDto.password,
    )

    assertIs<Result.Success<Unit>>(result)
    assertEquals(Unit, result.data)
  }
}