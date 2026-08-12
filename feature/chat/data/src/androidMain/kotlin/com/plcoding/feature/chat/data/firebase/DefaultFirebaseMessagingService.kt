package com.plcoding.feature.chat.data.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.plcoding.core.data.tools.PlatformUtils
import com.plcoding.core.domain.logger.Logger
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.feature.chat.domain.repository.DeviceTokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class DefaultFirebaseMessagingService : FirebaseMessagingService() {

  private val deviceTokenRepository by inject<DeviceTokenRepository>()
  private val preferencesRepository by inject<PreferencesRepository>()
  private val applicationScope by inject<CoroutineScope>()
  private val logger by inject<Logger>()

  override fun onNewToken(token: String) {
    super.onNewToken(token)

    logger.debug("onNewToken func faired | token: $token")

    applicationScope.launch {
      val authInfo = preferencesRepository.observeAuthInfo().firstOrNull()

      if (authInfo != null) {
        deviceTokenRepository.registerToken(token, PlatformUtils.OSName)
      }
    }
  }

  override fun onMessageReceived(message: RemoteMessage) {
    super.onMessageReceived(message)
    logger.debug("onMessageReceived faired | notification: ${message.notification}")
  }
}