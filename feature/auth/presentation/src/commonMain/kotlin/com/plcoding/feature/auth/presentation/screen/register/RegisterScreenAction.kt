package com.plcoding.feature.auth.presentation.screen.register

sealed interface RegisterScreenAction {

  data class OnTextFieldFocusGain(
    val isFocused: Boolean,
    val inputField: RegisterScreenViewModel.InputField,
  ) : RegisterScreenAction

  data object OnTextFieldSecureToggleClick : RegisterScreenAction
  data object OnPrimaryButtonClick : RegisterScreenAction
  data object OnSecondaryButtonClick : RegisterScreenAction
}
