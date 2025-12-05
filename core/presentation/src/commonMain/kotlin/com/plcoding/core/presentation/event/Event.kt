package com.plcoding.core.presentation.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

class Event<T>(private val data: T) {

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

  @Composable
  fun ConsumeSafely(action: suspend (T) -> Unit) {
    LaunchedEffect(data) {
      consumeInternal {
        action(data)
      }
    }
  }
  
  @Composable
  fun RunSafely(action: suspend () -> Unit) {
    LaunchedEffect(data) {
      consumeInternal {
        action()
      }
    }
  }

  private inline fun consumeInternal(action: (T) -> Unit) {
    if (!isConsumed) {
      isConsumed = true
      action(data)
    }
  }
}
