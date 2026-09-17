package com.plcoding.feature.auth.presentation.screen.email.verification

sealed interface EmailVerificationUiState {

  data object Loading: EmailVerificationUiState

  data object Failed : EmailVerificationUiState

  data object Success: EmailVerificationUiState
}
