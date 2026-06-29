package com.plcoding.chirp.screen.app

import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.feature.auth.presentation.navigation.AuthRoute
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AppScreenViewModel(
  private val preferencesRepository: PreferencesRepository,
) : BaseScreenViewModel<AppScreenContent>() {

  private var authInfo: AuthInfo? = null

  override fun getContentPm(): AppScreenContent {
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

      updateContentPm {
        copy(
          startDestination = startDestination,
          removeSplashScreenEvent = Event(Unit),
        )
      }
    }
  }

  private fun subscribeToAuthInfo() {
    preferencesRepository
      .observeAuthInfo()
      .onEach { authInfo ->
        if (this.authInfo != null && authInfo == null) {
          updateContentPm { copy(logoutEvent = Event(Unit)) }
        }
        this.authInfo = authInfo
      }
      .launchIn(viewModelScope)
  }
}
