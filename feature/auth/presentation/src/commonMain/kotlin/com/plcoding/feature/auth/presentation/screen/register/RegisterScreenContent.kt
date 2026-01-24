package com.plcoding.feature.auth.presentation.screen.register

import androidx.compose.foundation.text.input.TextFieldState
import chirp.core.presentation.generated.resources.chirp
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.log_in
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.register
import chirp.feature.auth.presentation.generated.resources.username
import chirp.feature.auth.presentation.generated.resources.welcome_to_chirp
import com.plcoding.core.designsystem.components.button.ButtonPcStyle
import org.jetbrains.compose.resources.StringResource
import chirp.core.presentation.generated.resources.Res as CoreRes

data class RegisterScreenContent(
  val titleRes: StringResource = Res.string.welcome_to_chirp,
  val errorRes: StringResource? = null,

  val usernameTopTitleRes: StringResource = Res.string.username,
  val usernameState: TextFieldState = TextFieldState(),
  val usernamePlaceholderRes: StringResource = CoreRes.string.chirp,
  val usernameBottomTitleRes: StringResource? = null,
  val usernameIsError: Boolean = false,

  val emailTopTitleRes: StringResource = Res.string.email,
  val emailState: TextFieldState = TextFieldState(),
  val emailPlaceholderRes: StringResource = Res.string.email,
  val emailBottomTitleRes: StringResource? = null,
  val emailIsError: Boolean = false,

  val passwordTopTitleRes: StringResource = Res.string.password,
  val passwordState: TextFieldState = TextFieldState(),
  val passwordPlaceholderRes: StringResource = Res.string.password,
  val passwordBottomTitleRes: StringResource? = null,
  val passwordIsError: Boolean = false,
  val passwordIsSecureMode: Boolean = false,

  val primaryButtonTitleRes: StringResource = Res.string.register,
  val primaryButtonPcStyle: ButtonPcStyle = ButtonPcStyle.PRIMARY,

  val secondaryButtonTitleRes: StringResource = Res.string.log_in,
  val secondaryButtonPcStyle: ButtonPcStyle = ButtonPcStyle.SECONDARY,
)
