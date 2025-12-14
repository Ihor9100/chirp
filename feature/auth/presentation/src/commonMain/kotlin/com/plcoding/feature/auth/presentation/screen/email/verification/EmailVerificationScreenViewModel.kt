package com.plcoding.feature.auth.presentation.screen.email.verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmailVerificationScreenViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
  savedStateHandle: SavedStateHandle,
) : BaseScreenViewModel<EmailVerificationScreenState>() {

  private var hasLoadedInitialData = false

  private val _state =
    MutableStateFlow<EmailVerificationScreenState>(EmailVerificationScreenState.Loading())
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
      initialValue = EmailVerificationScreenState.Loading()
    )

  private val token = savedStateHandle.get<String>("token")

  fun onAction(action: EmailVerificationScreenAction) = Unit

  private fun verifyEmail() {
    viewModelScope.launch {
      authRemoteRepository
        .verifyEmail(token ?: "")
        .onFailure { _state.update { EmailVerificationScreenState.Failed() } }
        .onSuccess { _state.update { EmailVerificationScreenState.Success() } }
    }
  }
}