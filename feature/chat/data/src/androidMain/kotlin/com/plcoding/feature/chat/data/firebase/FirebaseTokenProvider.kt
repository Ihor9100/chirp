package com.plcoding.feature.chat.data.firebase

import com.google.firebase.messaging.FirebaseMessaging
import com.plcoding.core.domain.logger.Logger
import com.plcoding.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

actual class FirebaseTokenProvider(
  private val preferencesRepository: PreferencesRepository,
  private val logger: Logger,
) {

  actual val token: Flow<String?> = flow {
    val authInfo = preferencesRepository.observeAuthInfo().firstOrNull()

    if (authInfo != null) {
      try {
        val token = FirebaseMessaging.getInstance().token.await()
        emit(token)
        logger.debug("FirebaseTokenProvider | New token: $token")
      } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        logger.error("FirebaseTokenProvider | Failed to get TOKEN", e)
      }
    } else {
      emit(null)
    }
  }
}