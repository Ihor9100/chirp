package com.plcoding.chirp.navigation

object ExternalUriHandler {

  private var cachedUri: String? = null

  var listener: ((String) -> Unit)? = null
    set(value) {
      field = value
      if (value != null) {
        cachedUri?.let { value(it) }
        cachedUri = null
      }
    }

  fun onNewUri(uri: String) {
    cachedUri = uri
    listener?.let {
      it.invoke(uri)
      cachedUri = null
    }
  }
}
