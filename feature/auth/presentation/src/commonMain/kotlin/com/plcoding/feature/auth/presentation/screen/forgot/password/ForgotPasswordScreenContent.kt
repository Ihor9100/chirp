package com.plcoding.feature.auth.presentation.screen.forgot.password

import androidx.compose.foundation.text.input.TextFieldState
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.forgot_password
import chirp.feature.auth.presentation.generated.resources.submit
import com.plcoding.core.designsystem.components.button.ButtonPcStyle
import com.plcoding.core.presentation.event.Event
import org.jetbrains.compose.resources.StringResource

data class ForgotPasswordScreenContent(
  val titleRes: StringResource = Res.string.forgot_password,
  val errorRes: StringResource? = null,

  val emailTopTitleRes: StringResource = Res.string.email,
  val emailState: TextFieldState = TextFieldState(),
  val emailPlaceholderRes: StringResource = Res.string.email,

  val primaryButtonTitleRes: StringResource = Res.string.submit,
  val primaryButtonPcStyle: ButtonPcStyle = ButtonPcStyle.PRIMARY,
  val primaryButtonIsEnable: Boolean = false,

  val snackbarEvent: Event<StringResource>? = null,
)
