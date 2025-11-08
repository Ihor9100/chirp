package com.plcoding.core.data.networking

import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(
  httpResponse: HttpResponse,
): Result<T, DataError.Remote> {
  return when (httpResponse.status.value) {
    in 200..299 -> {
      try {
        Result.Success(httpResponse.body<T>())
      } catch (e: NoTransformationFoundException) {
        Result.Failure(e)
      }
    }
    // TODO:
    else -> throw IllegalStateException()
  }
}

fun constructRoute(route: String): String {
  return when {
    route.contains(BASE_URL) -> route
    route.startsWith("/") -> BASE_URL + route
    else -> "$BASE_URL/$route"
  }
}