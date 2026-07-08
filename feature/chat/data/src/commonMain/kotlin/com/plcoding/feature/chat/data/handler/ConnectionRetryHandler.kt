package com.plcoding.feature.chat.data.handler

import com.plcoding.feature.chat.domain.handler.ConnectionErrorHandler
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

  suspend fun delayExponentially(attempt: Int) {
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

  private fun getDelayMillis(attempt: Int): Long {
    val delay = (2f.pow(attempt) * 1000).toLong()
    val maxDelay = 30_000L
    return min(delay, maxDelay)
  }
}