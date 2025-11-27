package com.plcoding.feature.auth.presentation.register

sealed interface RegisterAction {

  data class OnTextFieldFocusGain(
    val isFocused: Boolean,
    val inputField: RegisterViewModel.InputField,
  ) : RegisterAction

  data object OnTextFieldSecureToggleClick : RegisterAction
  data object OnPrimaryButtonClick : RegisterAction
  data object OnSecondaryButtonClick : RegisterAction
}
