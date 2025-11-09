package com.plcoding.core.data.networking

import com.plcoding.core.domain.Result
import com.plcoding.core.domain.utils.DataError
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

actual suspend fun <T> platformSafeCall(
  execute: suspend () -> HttpResponse,
  handleResponse: suspend (HttpResponse) -> Result<T, DataError.Remote>
): Result<T, DataError.Remote> {
  return try {
    handleResponse(execute())
  } catch (_: UnknownHostException) {
    Result.Failure(DataError.Remote.NO_INTERNET)
  } catch (_: UnresolvedAddressException) {
    Result.Failure(DataError.Remote.NO_INTERNET)
  } catch (_: ConnectException) {
    Result.Failure(DataError.Remote.NO_INTERNET)
  } catch (_: SocketTimeoutException) {
    Result.Failure(DataError.Remote.REQUEST_TIMEOUT)
  } catch (_: HttpRequestTimeoutException) {
    Result.Failure(DataError.Remote.REQUEST_TIMEOUT)
  } catch (_: SerializationException) {
    Result.Failure(DataError.Remote.SERIALIZATION)
  } catch (_: Exception) {
    currentCoroutineContext().ensureActive()
    Result.Failure(DataError.Remote.UNKNOWN)
  }
}