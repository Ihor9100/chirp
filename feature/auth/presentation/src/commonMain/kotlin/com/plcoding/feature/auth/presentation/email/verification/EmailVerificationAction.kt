package com.plcoding.feature.auth.presentation.email.verification

sealed interface EmailVerificationAction {
  data object OnCloseClick : EmailVerificationAction
  data object OnLogInClick : EmailVerificationAction
}