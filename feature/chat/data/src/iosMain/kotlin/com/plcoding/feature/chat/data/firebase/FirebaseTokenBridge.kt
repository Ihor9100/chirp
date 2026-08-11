package com.plcoding.feature.chat.data.firebase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FirebaseTokenBridge {

  private val _token = MutableStateFlow<String?>(null)
  val token: StateFlow<String?> = _token

  fun onNewToken(token: String?) {
    _token.value = token
  }
}