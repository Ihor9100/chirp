package com.plcoding.feature.auth.presentation.screen.login

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.event.Event
import org.jetbrains.compose.resources.StringResource

data class LoginUiState(
  val errorRes: StringResource? = null,
  val emailState: TextFieldState = TextFieldState(),
  val passwordState: TextFieldState = TextFieldState(),
  val passwordIsSecureMode: Boolean = false,
  val logInButtonIsEnabled: Boolean = true,
  val logInSuccessEvent: Event<Unit>? = null,
)