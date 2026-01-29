package com.plcoding.feature.auth.presentation.screen.reset.password

sealed interface ResetPasswordScreenAction {
  data object OnTextFieldSecureToggleClick : ResetPasswordScreenAction
  data object OnPrimaryButtonClick : ResetPasswordScreenAction
}