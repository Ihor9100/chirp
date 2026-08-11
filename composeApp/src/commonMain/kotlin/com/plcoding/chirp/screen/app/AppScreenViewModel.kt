package com.plcoding.chirp.screen.app

import androidx.lifecycle.viewModelScope
import com.plcoding.core.data.tools.PlatformUtils
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
) : BaseScreenViewModel<AppScreenContent>() {

  private var authInfo: AuthInfo? = null
  private var firebaseToken: String? = null

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
        if (authInfo != null && it == null) {
          updateUiState { copy(logoutEvent = Event(Unit)) }
        }

        if (it == null && firebaseToken != null) {
          deviceTokenRepository.unregisterToken(firebaseToken!!)
        }

        authInfo = it
      }
      .combine(deviceTokenRepository.token) { authInfo, firebaseToken ->
        this.firebaseToken = firebaseToken

        if (authInfo != null && firebaseToken != null) {
          deviceTokenRepository.registerToken(firebaseToken, PlatformUtils.OSName)
        }
      }
      .launchIn(viewModelScope)
  }
}
