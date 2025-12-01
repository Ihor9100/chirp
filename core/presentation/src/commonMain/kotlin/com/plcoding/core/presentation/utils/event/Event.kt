package com.plcoding.core.presentation.utils.event

sealed interface Event<T> {
  val data: T
  val onConsumed: () -> Unit

  fun consume(): T {
    onConsumed()
    return data
  }
}
