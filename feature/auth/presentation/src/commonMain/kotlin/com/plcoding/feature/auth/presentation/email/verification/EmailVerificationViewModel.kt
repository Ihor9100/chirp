package com.plcoding.feature.auth.presentation.email.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.networking.service.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class EmailVerificationViewModel(
  private val authService: AuthService,
) : ViewModel() {

  private var hasLoadedInitialData = false

  private val _state = MutableStateFlow<EmailVerificationState>(EmailVerificationState.Loading())
  val state = _state
    .onStart {
      if (!hasLoadedInitialData) {
        /** Load initial data here **/
        hasLoadedInitialData = true
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000L),
      initialValue = EmailVerificationState.Loading()
    )

  fun onAction(action: EmailVerificationAction) {
    when (action) {
      EmailVerificationAction.OnCloseClick -> TODO()
      EmailVerificationAction.OnLogInClick -> TODO()
    }
  }
}