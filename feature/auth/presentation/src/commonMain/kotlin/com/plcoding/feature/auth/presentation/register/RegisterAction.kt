package com.plcoding.feature.auth.presentation.register

sealed interface RegisterAction {
  data object OnTextFieldFocusGain : RegisterAction
  data object OnTextFieldSecureToggleClick : RegisterAction
  data object OnPrimaryButtonClick : RegisterAction
  data object OnSecondaryButtonClick : RegisterAction
}
