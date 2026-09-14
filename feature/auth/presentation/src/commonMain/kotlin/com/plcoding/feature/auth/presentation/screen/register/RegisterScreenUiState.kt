package com.plcoding.feature.auth.presentation.screen.register

import androidx.compose.foundation.text.input.TextFieldState
import org.jetbrains.compose.resources.StringResource

data class RegisterScreenUiState(
  val errorRes: StringResource? = null,

  val usernameState: TextFieldState = TextFieldState(),
  val usernameBottomTitleRes: StringResource? = null,
  val usernameIsError: Boolean = false,

  val emailState: TextFieldState = TextFieldState(),
  val emailBottomTitleRes: StringResource? = null,
  val emailIsError: Boolean = false,

  val passwordState: TextFieldState = TextFieldState(),
  val passwordBottomTitleRes: StringResource? = null,
  val passwordIsError: Boolean = false,
  val passwordIsSecureMode: Boolean = false,
)
