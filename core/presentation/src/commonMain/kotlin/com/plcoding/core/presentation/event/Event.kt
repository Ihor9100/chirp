package com.plcoding.core.presentation.event

data class Event<T>(
  private val data: T,
  private val onConsumed: (() -> Unit)? = null,
) {

  private var isConsumed: Boolean = false

  fun run(action: () -> Unit) {
    consumeInternal {
      action()
    }
  }

  fun consume(action: (T) -> Unit) {
    consumeInternal {
      action(data)
    }
  }

  suspend fun runAsync(action: suspend () -> Unit) {
    consumeInternal {
      action()
    }
  }

  suspend fun consumeAsync(action: suspend (T) -> Unit) {
    consumeInternal {
      action(data)
    }
  }

  private inline fun consumeInternal(action: (T) -> Unit) {
    if (!isConsumed) {
      isConsumed = true
      onConsumed?.invoke()
      action(data)
    }
  }
}
