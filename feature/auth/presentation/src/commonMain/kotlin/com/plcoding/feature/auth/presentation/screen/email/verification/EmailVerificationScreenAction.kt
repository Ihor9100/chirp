package com.plcoding.feature.auth.presentation.screen.email.verification

sealed interface EmailVerificationScreenAction {
  data object OnCloseClick : EmailVerificationScreenAction
  data object OnLogInClick : EmailVerificationScreenAction
}