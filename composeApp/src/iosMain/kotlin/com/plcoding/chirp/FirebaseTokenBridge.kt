package com.plcoding.chirp

import com.plcoding.feature.chat.data.firebase.FirebaseTokenBridge

object FirebaseTokenBridge {
  fun onNewToken(token: String?) {
    FirebaseTokenBridge.onNewToken(token)
  }
}
