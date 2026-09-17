package com.plcoding.feature.auth.presentation.screen.reset.password

sealed interface ResetPasswordScreenAction {
  data object OnPasswordSecureToggleClick : ResetPasswordScreenAction
  data object OnSubmitButtonClick : ResetPasswordScreenAction
}