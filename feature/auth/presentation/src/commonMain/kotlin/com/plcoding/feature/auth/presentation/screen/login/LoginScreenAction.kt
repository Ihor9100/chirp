package com.plcoding.feature.auth.presentation.screen.login

sealed interface LoginScreenAction {
  data object OnPasswordSecureToggleClick : LoginScreenAction
  data object OnForgotPasswordClick : LoginScreenAction
  data object OnLoginClick : LoginScreenAction
  data object OnRegisterClick : LoginScreenAction
}