package com.plcoding.core.presentation.utils

class NavResult {

  private val listeners: MutableMap<String, (Any) -> Unit> = mutableMapOf()

  @Suppress("UNCHECKED_CAST")
  fun <T> addListener(key: String, listener: (T?) -> Unit) {
    listeners[key] = {
      listener(it as? T)
    }
  }

  fun setResult(key: String, result: Any) {
    val listener = listeners.remove(key)
    listener?.invoke(result)
  }
}
