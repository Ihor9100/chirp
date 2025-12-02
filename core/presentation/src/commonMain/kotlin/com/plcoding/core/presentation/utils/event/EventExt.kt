package com.plcoding.core.presentation.utils.event

inline fun <T> Event<T>.consume(action: (T) -> Unit) {
  action(data)
  onConsumed()
}
