package com.plcoding.core.presentation.event

sealed interface Event<T> {
  val data: T
  val onConsumed: () -> Unit
}
