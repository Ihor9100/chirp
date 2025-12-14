package com.plcoding.feature.auth.presentation.screen.login

sealed interface LoginScreenAction {
  data object OnTextFieldSecureToggleClick : LoginScreenAction
  data object OnForgotPasswordClick : LoginScreenAction
  data object OnPrimaryButtonClick : LoginScreenAction
  data object OnSecondaryButtonClick : LoginScreenAction
}