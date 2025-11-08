package com.plcoding.core.data.networking

import com.plcoding.core.domain.Result
import com.plcoding.core.domain.utils.DataError
import io.ktor.client.statement.HttpResponse
import java.net.UnknownHostException

actual suspend fun <T> platformSafeCall(
  execute: suspend () -> HttpResponse,
  handleResponse: suspend (HttpResponse) -> Result<T, DataError.Remote>
): Result<T, DataError.Remote> {
  return try {
    handleResponse(execute())
  } catch (_: UnknownHostException) {
    Result.Failure(DataError.Remote.NO_INTERNET)
  } catch (_: UnknownHostException) {
    Result.Failure(DataError.Remote.NO_INTERNET)
  }
}