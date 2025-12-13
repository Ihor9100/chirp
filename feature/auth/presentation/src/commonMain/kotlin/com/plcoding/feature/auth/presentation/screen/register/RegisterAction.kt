package com.plcoding.feature.auth.presentation.screen.register

sealed interface RegisterAction {

  data class OnTextFieldFocusGain(
    val isFocused: Boolean,
    val inputField: RegisterScreenViewModel.InputField,
  ) : RegisterAction

  data object OnTextFieldSecureToggleClick : RegisterAction
  data object OnPrimaryButtonClick : RegisterAction
  data object OnSecondaryButtonClick : RegisterAction
}
