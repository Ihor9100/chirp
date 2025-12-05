package com.plcoding.feature.auth.presentation.screen.login

sealed interface LoginAction {
  data object OnTextFieldSecureToggleClick : LoginAction
  data object OnForgotPasswordClick : LoginAction
  data object OnPrimaryButtonClick : LoginAction
  data object OnSecondaryButtonClick : LoginAction
}