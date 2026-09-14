package com.plcoding.feature.auth.presentation.screen.register

sealed interface RegisterScreenAction {

  data class OnTextFieldFocusGain(
    val isFocused: Boolean,
    val inputField: RegisterScreenViewModel.InputField,
  ) : RegisterScreenAction

  data object OnPasswordSecureIconClick : RegisterScreenAction
  data object OnRegisterClick : RegisterScreenAction
  data object OnLoginClick : RegisterScreenAction
}
