package com.plcoding.feature.auth.presentation.screen.reset.password

import androidx.compose.material3.SnackbarResult

sealed interface ResetPasswordScreenAction {
  data object OnTextFieldSecureToggleClick : ResetPasswordScreenAction
  data object OnPrimaryButtonClick : ResetPasswordScreenAction
  data class OnSnackbarDisappeared(val snackbarResult: SnackbarResult) : ResetPasswordScreenAction
}