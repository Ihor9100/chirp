@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.feature.chat.data.firebase

import com.google.firebase.messaging.FirebaseMessaging
import com.plcoding.core.domain.logger.Logger
import com.plcoding.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

actual class FirebaseTokenProvider(
  private val logger: Logger,
  preferencesRepository: PreferencesRepository,
) {

  actual val token: Flow<String?> = preferencesRepository
    .observeAuthInfo()
    .flatMapLatest { authInfo ->
      flow {
        if (authInfo != null) {
          val token = FirebaseMessaging.getInstance().token.await()
          logger.debug("FirebaseTokenProvider | New token: $token")
          emit(token)
        } else {
          emit(null)
        }
      }.catch {
        logger.error("FirebaseTokenProvider | Failed to get TOKEN", it)
        emit(null)
      }
    }
}