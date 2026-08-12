package com.plcoding.chirp.screen.app

import androidx.lifecycle.viewModelScope
import com.plcoding.core.data.tools.PlatformUtils
import com.plcoding.core.domain.logger.Logger
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.feature.auth.presentation.navigation.AuthRoute
import com.plcoding.feature.chat.domain.repository.DeviceTokenRepository
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AppScreenViewModel(
  private val preferencesRepository: PreferencesRepository,
  private val deviceTokenRepository: DeviceTokenRepository,
  private val logger: Logger,
) : BaseScreenViewModel<AppScreenContent>() {

  private var currentAuthInfo: AuthInfo? = null
  private var currentFirebaseToken: String? = null

  override fun getUiState(): AppScreenContent {
    return AppScreenContent()
  }

  override fun onInitialize() {
    super.onInitialize()

    updateStartDestination()
    subscribeToAuthInfo()
  }

  private fun updateStartDestination() {
    viewModelScope.launch {
      val authInfo = preferencesRepository.observeAuthInfo().firstOrNull()

      val startDestination = if (authInfo == null) {
        AuthRoute.Graph
      } else {
        ChatRoute.Graph
      }

      updateUiState {
        copy(
          startDestination = startDestination,
        )
      }
    }
  }

  private fun subscribeToAuthInfo() {
    preferencesRepository
      .observeAuthInfo()
      .onEach {
        if (currentAuthInfo != null && it == null) {
          updateUiState { copy(logoutEvent = Event(Unit)) }
        }

        if (it == null && currentFirebaseToken != null) {
          deviceTokenRepository.unregisterToken(currentFirebaseToken!!)
        }

        logger.debug("subscribeToAuthInfo | onEach | authInfo: $it")
        currentAuthInfo = it
      }
      .combine(deviceTokenRepository.token) { authInfo, firebaseToken ->
        logger.debug("subscribeToAuthInfo | combine | authInfo: $authInfo | firebaseToken: $firebaseToken")

        val registerToken = authInfo != null &&
          firebaseToken != null &&
          currentFirebaseToken != firebaseToken

        if (registerToken) {
          deviceTokenRepository.registerToken(firebaseToken, PlatformUtils.OSName)
        }

        currentFirebaseToken = firebaseToken
      }
      .launchIn(viewModelScope)
  }
}
