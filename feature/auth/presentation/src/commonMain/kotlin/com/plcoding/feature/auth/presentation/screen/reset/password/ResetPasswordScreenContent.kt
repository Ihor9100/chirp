package com.plcoding.feature.auth.presentation.screen.reset.password

import androidx.compose.foundation.text.input.TextFieldState
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.hint_password
import chirp.feature.auth.presentation.generated.resources.new_password
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.register
import chirp.feature.auth.presentation.generated.resources.welcome_to_chirp
import com.plcoding.core.designsystem.components.button.ChirpButtonStyle
import com.plcoding.core.presentation.event.Event
import org.jetbrains.compose.resources.StringResource

data class ResetPasswordScreenContent(
  val titleRes: StringResource = Res.string.welcome_to_chirp,
  val errorRes: StringResource? = null,

  val passwordTopTitleRes: StringResource = Res.string.new_password,
  val passwordState: TextFieldState = TextFieldState(),
  val passwordPlaceholderRes: StringResource = Res.string.password,
  val passwordBottomTitleRes: StringResource = Res.string.hint_password,
  val passwordIsEnable: Boolean = false,
  val passwordIsError: Boolean = false,
  val passwordIsSecureMode: Boolean = false,

  val primaryButtonTitleRes: StringResource = Res.string.register,
  val primaryButtonStyle: ChirpButtonStyle = ChirpButtonStyle.PRIMARY,

  val resetSuccessEvent: Event<StringResource>? = null,
  val navigateToLoginEvent: Event<Unit>? = null,
)
