package com.plcoding.feature.chat.data.firebase

import kotlinx.coroutines.flow.onStart
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIApplication
import platform.UIKit.registerForRemoteNotifications

actual class FirebaseTokenProvider {

  companion object {
    const val FCM_TOKEN_KEY = "FCM_TOKEN_KEY"
  }

  actual val token = FirebaseTokenBridge
    .token
    .onStart {
      if (FirebaseTokenBridge.token.value == null) {
        val token = NSUserDefaults.standardUserDefaults.stringForKey(FCM_TOKEN_KEY)

        if (token != null) {
          FirebaseTokenBridge.onNewToken(token)
        } else {
          UIApplication.sharedApplication.registerForRemoteNotifications()
        }
      }
    }
}