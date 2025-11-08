package com.plcoding.core.data.networking

import com.plcoding.core.domain.Result
import com.plcoding.core.domain.utils.DataError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

expect suspend fun <T> platformSafeCall(
  execute: suspend () -> HttpResponse,
  handleResponse: suspend (HttpResponse) -> Result<T, DataError.Remote>
): Result<T, DataError.Remote>

suspend inline fun <reified T> safeCall(
  noinline execute: suspend () -> HttpResponse,
): Result<T, DataError.Remote> {
  return platformSafeCall(execute, ::responseToResult)
}

suspend inline fun <reified T> responseToResult(
  httpResponse: HttpResponse,
): Result<T, DataError.Remote> {
  return when (httpResponse.status.value) {
    in 200..299 -> {
      try {
        Result.Success(httpResponse.body<T>())
      } catch (_: NoTransformationFoundException) {
        Result.Failure(DataError.Remote.SERIALIZATION)
      }
    }
    400 -> Result.Failure(DataError.Remote.BAD_REQUEST)
    401 -> Result.Failure(DataError.Remote.UNAUTHORIZED)
    403 -> Result.Failure(DataError.Remote.FORBIDDEN)
    404 -> Result.Failure(DataError.Remote.NOT_FOUND)
    408 -> Result.Failure(DataError.Remote.REQUEST_TIMEOUT)
    409 -> Result.Failure(DataError.Remote.CONFLICT)
    413 -> Result.Failure(DataError.Remote.PAYLOAD_TOO_LARGE)
    429 -> Result.Failure(DataError.Remote.TOO_MANY_REQUESTS)
    500 -> Result.Failure(DataError.Remote.SERVER_ERROR)
    503 -> Result.Failure(DataError.Remote.SERVER_UNAVAILABLE)
    else -> Result.Failure(DataError.Remote.UNKNOWN)
  }
}

fun constructRoute(route: String): String {
  return when {
    route.contains(BASE_URL) -> route
    route.startsWith("/") -> BASE_URL + route
    else -> "$BASE_URL/$route"
  }
}