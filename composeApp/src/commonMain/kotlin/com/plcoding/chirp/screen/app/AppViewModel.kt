package com.plcoding.chirp.screen.app

import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.local.PreferencesLocalRepository
import com.plcoding.core.presentation.base.BaseViewModel
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.auth.presentation.navigation.AuthRoute
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
  private val preferencesLocalRepository: PreferencesLocalRepository,
) : BaseViewModel<AppState>() {

  private var authInfo: AuthInfo? = null

  override fun getInitialState(): AppState {
    return AppState()
  }

  override fun onInitialized() {
    super.onInitialized()

    updateStartDestination()
    subscribeToAuthInfo()
  }

  private fun updateStartDestination() {
    viewModelScope.launch {
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

  private fun subscribeToAuthInfo() {
    preferencesLocalRepository
      .observeAuthInfo()
      .onEach { authInfo ->
        if (this.authInfo != null && authInfo == null) {
          mutableState.update {
            it.copy(logoutEvent = Event(Unit))
          }
        }
        this.authInfo = authInfo
      }
      .launchIn(viewModelScope)
  }
}
