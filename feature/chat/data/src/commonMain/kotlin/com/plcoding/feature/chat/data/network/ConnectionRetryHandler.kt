package com.plcoding.feature.chat.data.network

import com.plcoding.feature.chat.domain.network.ConnectionErrorHandler
import kotlinx.coroutines.delay
import kotlin.math.min
import kotlin.math.pow

class ConnectionRetryHandler(
  private val connectionErrorHandler: ConnectionErrorHandler,
) {

  private var useExponentialDelay = true

  fun shouldRetry(throwable: Throwable): Boolean {
    return connectionErrorHandler.isRetriableError(throwable)
  }

  suspend fun delayExponentially(attempt: Long) {
    if (useExponentialDelay) {
      val delay = getDelayMillis(attempt)
      delay(delay)
    } else {
      useExponentialDelay = true
    }
  }

  fun reset() {
    useExponentialDelay = false
  }

  private fun getDelayMillis(attempt: Long): Long {
    val delay = (2f.pow(attempt.toInt()) * 1000).toLong()
    val maxDelay = 30_000L
    return min(delay, maxDelay)
  }
}