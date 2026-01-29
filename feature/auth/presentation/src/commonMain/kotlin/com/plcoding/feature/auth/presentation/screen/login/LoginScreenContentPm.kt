package com.plcoding.feature.auth.presentation.screen.login

import androidx.compose.foundation.text.input.TextFieldState
import chirp.core.presentation.generated.resources.chirp
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.create_account
import chirp.feature.auth.presentation.generated.resources.log_in
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.username_or_email
import chirp.feature.auth.presentation.generated.resources.welcome_back
import com.plcoding.core.designsystem.components.button.ButtonPcStyle
import com.plcoding.core.presentation.event.Event
import org.jetbrains.compose.resources.StringResource
import chirp.core.presentation.generated.resources.Res as CoreRes

data class LoginScreenContentPm(
  val titleRes: StringResource = Res.string.welcome_back,
  val errorRes: StringResource? = null,

  val emailTopTitleRes: StringResource = Res.string.username_or_email,
  val emailState: TextFieldState = TextFieldState(),
  val emailPlaceholderRes: StringResource = CoreRes.string.chirp,

  val passwordTopTitleRes: StringResource = Res.string.password,
  val passwordState: TextFieldState = TextFieldState(),
  val passwordPlaceholderRes: StringResource = Res.string.password,
  val passwordIsSecureMode: Boolean = false,

  val primaryButtonTitleRes: StringResource = Res.string.log_in,
  val primaryButtonPcStyle: ButtonPcStyle = ButtonPcStyle.PRIMARY,
  val primaryButtonIsEnable: Boolean = true,

  val secondaryButtonTitleRes: StringResource = Res.string.create_account,
  val secondaryButtonPcStyle: ButtonPcStyle = ButtonPcStyle.SECONDARY,
  
  val logInSuccessEvent: Event<Unit>? = null,
)