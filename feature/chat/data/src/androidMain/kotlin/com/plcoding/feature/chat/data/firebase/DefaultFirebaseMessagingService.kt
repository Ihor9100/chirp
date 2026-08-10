package com.plcoding.feature.chat.data.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.plcoding.core.data.tools.PlatformUtils
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.feature.chat.domain.repository.FirebaseTokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class DefaultFirebaseMessagingService : FirebaseMessagingService() {

  private val firebaseTokenRepository by inject<FirebaseTokenRepository>()
  private val preferencesRepository by inject<PreferencesRepository>()
  private val applicationScope by inject<CoroutineScope>()

  override fun onNewToken(token: String) {
    super.onNewToken(token)

    applicationScope.launch {
      val authInfo = preferencesRepository.observeAuthInfo().firstOrNull()

      if (authInfo != null) {
        firebaseTokenRepository.registerFirebaseToken(token, PlatformUtils.OSName)
      }
    }
  }
}