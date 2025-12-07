package com.plcoding.feature.auth.presentation.screen.email.verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class   EmailVerificationViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
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

  fun onAction(action: EmailVerificationAction) = Unit

  private fun verifyEmail() {
    viewModelScope.launch {
      authRemoteRepository
        .verifyEmail(token ?: "")
        .onFailure { _state.update { EmailVerificationState.Failed() } }
        .onSuccess { _state.update { EmailVerificationState.Success() } }
    }
  }
}