package com.plcoding.feature.auth.presentation.screen.reset.password

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.event.Event
import org.jetbrains.compose.resources.StringResource

data class ResetPasswordUiState(
  val errorRes: StringResource? = null,
  val passwordState: TextFieldState = TextFieldState(),
  val passwordIsError: Boolean = false,
  val passwordIsSecureMode: Boolean = true,
  val submitButtonIsEnabled: Boolean = false,
  val navigateToLoginEvent: Event<Unit>? = null,
)
