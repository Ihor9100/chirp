package com.plcoding.feature.auth.presentation.screen.email.verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.repository.AuthRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import kotlinx.coroutines.launch

class EmailVerificationScreenViewModel(
  private val authRepository: AuthRepository,
  savedStateHandle: SavedStateHandle,
) : BaseScreenViewModel<EmailVerificationUiState>() {

  private val token = savedStateHandle.get<String>("token")

  override fun getUiState(): EmailVerificationUiState {
    return EmailVerificationUiState.Loading()
  }

  override fun onInitialize() {
    super.onInitialize()
    verifyEmail()
  }

  private fun verifyEmail() {
    viewModelScope.launch {
      authRepository
        .verifyEmail(token ?: "")
        .onFailure { updateUiState { EmailVerificationUiState.Failed() } }
        .onSuccess { updateUiState { EmailVerificationUiState.Success() } }
    }
  }

  fun onAction(action: EmailVerificationScreenAction) = Unit
}
