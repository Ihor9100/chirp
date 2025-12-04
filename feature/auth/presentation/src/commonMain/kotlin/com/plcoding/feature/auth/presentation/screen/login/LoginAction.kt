package com.plcoding.feature.auth.presentation.screen.login

sealed interface LoginAction {

  data class OnTextFieldFocusGain(
    val isFocused: Boolean,
    val inputField: LoginViewModel.InputField,
  ) : LoginAction

  data object OnTextFieldSecureToggleClick : LoginAction
  data object OnPrimaryButtonClick : LoginAction
  data object OnSecondaryButtonClick : LoginAction
}