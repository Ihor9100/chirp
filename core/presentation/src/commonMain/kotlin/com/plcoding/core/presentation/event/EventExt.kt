package com.plcoding.core.presentation.event

inline fun <T> Event<T>.consume(action: (T) -> Unit) {
  action(data)
  onConsumed()
}
