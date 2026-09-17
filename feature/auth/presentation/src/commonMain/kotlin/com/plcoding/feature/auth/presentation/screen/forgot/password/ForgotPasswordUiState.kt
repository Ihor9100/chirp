package com.plcoding.feature.auth.presentation.screen.forgot.password

import androidx.compose.foundation.text.input.TextFieldState
import org.jetbrains.compose.resources.StringResource

data class ForgotPasswordUiState(
  val errorRes: StringResource? = null,
  val emailState: TextFieldState = TextFieldState(),
  val submitButtonIsEnabled: Boolean = false,
)
