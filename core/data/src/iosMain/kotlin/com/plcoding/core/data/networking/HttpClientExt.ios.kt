package com.plcoding.core.data.networking

import com.plcoding.core.domain.Result
import com.plcoding.core.domain.utils.DataError
import io.ktor.client.engine.darwin.DarwinHttpRequestException
import io.ktor.client.statement.HttpResponse
import platform.Foundation.NSURLErrorCallIsActive
import platform.Foundation.NSURLErrorCannotFindHost
import platform.Foundation.NSURLErrorDNSLookupFailed
import platform.Foundation.NSURLErrorDataNotAllowed
import platform.Foundation.NSURLErrorDomain
import platform.Foundation.NSURLErrorInternationalRoamingOff
import platform.Foundation.NSURLErrorNetworkConnectionLost
import platform.Foundation.NSURLErrorNotConnectedToInternet
import platform.Foundation.NSURLErrorResourceUnavailable
import platform.Foundation.NSURLErrorTimedOut

actual suspend fun <T> platformSafeCall(
  execute: suspend () -> HttpResponse,
  handleResponse: suspend (HttpResponse) -> Result<T, DataError.Remote>
): Result<T, DataError.Remote> {
  return try {
    handleResponse(execute())
  } catch (e: DarwinHttpRequestException) {
    handleDarwinException(e)
  }
}

private fun handleDarwinException(exception: DarwinHttpRequestException): Result<Nothing, DataError.Remote> {
  return if (exception.origin.domain == NSURLErrorDomain) {
    when (exception.origin.code) {
      NSURLErrorNotConnectedToInternet,
      NSURLErrorNetworkConnectionLost,
      NSURLErrorCannotFindHost,
      NSURLErrorDNSLookupFailed,
      NSURLErrorResourceUnavailable,
      NSURLErrorInternationalRoamingOff,
      NSURLErrorCallIsActive,
      NSURLErrorDataNotAllowed -> Result.Failure(DataError.Remote.NO_INTERNET)
      NSURLErrorTimedOut -> Result.Failure(DataError.Remote.REQUEST_TIMEOUT)
      else -> Result.Failure(DataError.Remote.UNKNOWN)
    }
  } else {
    Result.Failure(DataError.Remote.UNKNOWN)
  }
}