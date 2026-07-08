package com.plcoding.feature.chat.data.handler

import com.plcoding.chat.domain.models.ConnectionState
import com.plcoding.feature.chat.domain.handler.ConnectionErrorHandler
import kotlinx.coroutines.CancellationException
import platform.Foundation.NSError
import platform.Foundation.NSURLErrorDomain
import platform.Foundation.NSURLErrorNetworkConnectionLost
import platform.Foundation.NSURLErrorNotConnectedToInternet
import platform.Foundation.NSURLErrorTimedOut

class iOSConnectionErrorHandler: ConnectionErrorHandler {

    override fun getConnectionState(throwable: Throwable): ConnectionState {
        val nsError = extractNsError(throwable)

        return if(nsError != null) {
            when(nsError.code) {
                NSURLErrorNotConnectedToInternet,
                NSURLErrorNetworkConnectionLost,
                NSURLErrorTimedOut -> ConnectionState.ERROR_NETWORK
                else -> ConnectionState.ERROR_UNKNOWN
            }
        } else if(throwable is IOSNetworkCancellationException) {
            ConnectionState.ERROR_NETWORK
        } else ConnectionState.ERROR_UNKNOWN
    }

    override fun transformException(throwable: Throwable): Throwable {
        if(throwable is CancellationException) {
            val cause = throwable.cause ?: return throwable
            val isDarwinException = cause.message?.contains("DarwinHttpRequestException") == true
            val isConnectionLostException = cause.message?.contains("NSURLErrorDomain Code=-1005") == true
            val isNotConnectedException = cause.message?.contains("NSURLErrorDomain Code=-1009") == true

            if(isDarwinException || isConnectionLostException || isNotConnectedException) {
                return IOSNetworkCancellationException(
                    message = "Network connection lost (extracted from cancellation)",
                    cause = cause
                )
            }
        }

        return throwable
    }

    override fun isRetriableError(throwable: Throwable): Boolean {
        if(throwable is IOSNetworkCancellationException) {
            return true
        }

        return when(extractNsError(throwable)?.code) {
            NSURLErrorNotConnectedToInternet,
            NSURLErrorNetworkConnectionLost,
            NSURLErrorTimedOut -> true
            else -> false
        }
    }

    private fun extractNsError(throwable: Throwable): NSError? {
        val throwableCause = throwable.cause
        if (throwableCause is NSError) {
            return throwableCause
        }

        if (throwable is NSError) {
            return throwable
        }

        val exceptionNsError = throwable.toNSError()
        val causeNsError = throwable.cause?.toNSError()

        return exceptionNsError ?: causeNsError
    }

    private fun Throwable.toNSError(): NSError? {
        return message?.let { message ->
            when {
                message.contains(NSURLErrorNotConnectedToInternetPattern) ->
                    return NSError.errorWithDomain(
                        domain = NSURLErrorDomain,
                        code = NSURLErrorNotConnectedToInternet,
                        userInfo = null
                    )
                message.contains(NSURLErrorNetworkConnectionLostPattern) ->
                    return NSError.errorWithDomain(
                        domain = NSURLErrorDomain,
                        code = NSURLErrorNetworkConnectionLost,
                        userInfo = null
                    )
                else -> null
            }
        }
    }

    companion object {
        private val NSURLErrorNotConnectedToInternetPattern =
            "Error Domain=${NSURLErrorDomain} Code=${NSURLErrorNotConnectedToInternet}"
        val NSURLErrorNetworkConnectionLostPattern =
            "Error Domain=${NSURLErrorDomain} Code=${NSURLErrorNetworkConnectionLost}"
    }
}