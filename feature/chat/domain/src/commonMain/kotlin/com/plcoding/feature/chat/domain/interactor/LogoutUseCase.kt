package com.plcoding.feature.chat.domain.interactor

import com.plcoding.core.domain.logger.Logger
import com.plcoding.core.domain.repository.AuthRepository
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.domain.repository.DeviceTokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull

class LogoutUseCase(
  private val preferencesRepository: PreferencesRepository,
  private val deviceTokenRepository: DeviceTokenRepository,
  private val authRepository: AuthRepository,
  private val chatRepository: ChatRepository,
  private val logger: Logger,
) {

  suspend operator fun invoke(): Empty<DataError> {
    return coroutineScope {
      val deviceToken = deviceTokenRepository.token.firstOrNull()
      val authInfo = preferencesRepository.observeAuthInfo().firstOrNull()

      if (deviceToken == null || authInfo == null) {
        logger.debug("Failed to logout | DeviceToken: $deviceToken | AuthInfo: $authInfo")
        return@coroutineScope Result.Failure(DataError.Local.NOT_FOUND)
      }

      deviceTokenRepository
        .unregisterToken(deviceToken)
        .onSuccess {
          return@coroutineScope authRepository
            .logout(authInfo.refreshToken)
            .onSuccess { awaitAll(deleteChatsDeferred(), deleteAuthInfoDeferred()) }
        }
    }
  }

  private fun CoroutineScope.deleteChatsDeferred(): Deferred<Unit> {
    return async {
      chatRepository.deleteChats()
    }
  }

  private fun CoroutineScope.deleteAuthInfoDeferred(): Deferred<Unit> {
    return async {
      preferencesRepository.saveAuthInfo(null)
    }
  }
}