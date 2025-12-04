package com.plcoding.feature.auth.presentation.screen.email.verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.network.service.AuthService
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class   EmailVerificationViewModel(
  private val authService: AuthService,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private var hasLoadedInitialData = false

  private val _state = MutableStateFlow<EmailVerificationState>(EmailVerificationState.Loading())
  val state = _state
    .onStart {
      if (!hasLoadedInitialData) {
        verifyEmail()
        /** Load initial data here **/
        hasLoadedInitialData = true
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000L),
      initialValue = EmailVerificationState.Loading()
    )

  private val token = savedStateHandle.get<String>("token")

  fun onAction(action: EmailVerificationAction) {
    when (action) {
      EmailVerificationAction.OnCloseClick -> TODO()
      EmailVerificationAction.OnLogInClick -> TODO()
    }
  }

  private fun verifyEmail() {
    viewModelScope.launch {
      authService
        .verifyEmail(token ?: "")
        .onFailure { _state.update { EmailVerificationState.Failed() } }
        .onSuccess { _state.update { EmailVerificationState.Success() } }
    }
  }
}