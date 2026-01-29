package com.plcoding.feature.auth.presentation.screen.email.verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import kotlinx.coroutines.launch

class EmailVerificationScreenViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
  savedStateHandle: SavedStateHandle,
) : BaseScreenViewModel<EmailVerificationScreenContentPm>() {

  private val token = savedStateHandle.get<String>("token")

  override fun getContentPm(): EmailVerificationScreenContentPm {
    return EmailVerificationScreenContentPm.Loading()
  }

  override fun onInitialized() {
    super.onInitialized()
    verifyEmail()
  }

  private fun verifyEmail() {
    viewModelScope.launch {
      authRemoteRepository
        .verifyEmail(token ?: "")
        .onFailure { updateContentPm { EmailVerificationScreenContentPm.Failed() } }
        .onSuccess { updateContentPm { EmailVerificationScreenContentPm.Success() } }
    }
  }

  fun onAction(action: EmailVerificationScreenAction) = Unit
}
