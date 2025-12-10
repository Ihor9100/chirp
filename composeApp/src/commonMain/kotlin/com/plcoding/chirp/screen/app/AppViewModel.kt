package com.plcoding.chirp.screen.app

import com.plcoding.core.domain.repository.local.PreferencesLocalRepository
import com.plcoding.core.presentation.base.BaseViewModel
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.auth.presentation.navigation.AuthRoute
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class AppViewModel(
  private val preferencesLocalRepository: PreferencesLocalRepository,
) : BaseViewModel<AppState>() {

  override fun getInitialState() = AppState()

  override suspend fun onInitialized() {
    super.onInitialized()

    updateStartDestination()
    subscribeToAuthInfo()
  }

  private suspend fun subscribeToAuthInfo() {
    preferencesLocalRepository
      .observeAuthInfo()
      .onEach { authInfo ->
        if (authInfo?.refreshToken == null) {
          mutableState.update {
            it.copy(logoutEvent = Event(Unit))
          }
        }
      }
      .collect()
  }

  private suspend fun updateStartDestination() {
    val authInfo = preferencesLocalRepository.observeAuthInfo().firstOrNull()

    val startDestination = if (authInfo == null) {
      AuthRoute.Graph
    } else {
      ChatRoute.Graph
    }

    mutableState.update {
      it.copy(
        startDestination = startDestination,
        removeSplashScreenEvent = Event(Unit),
      )
    }
  }
}
