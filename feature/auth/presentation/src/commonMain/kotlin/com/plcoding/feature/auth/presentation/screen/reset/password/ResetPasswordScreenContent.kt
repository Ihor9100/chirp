package com.plcoding.feature.auth.presentation.screen.reset.password

import androidx.compose.foundation.text.input.TextFieldState
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.hint_password
import chirp.feature.auth.presentation.generated.resources.new_password
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.set_new_password
import chirp.feature.auth.presentation.generated.resources.submit
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.presentation.event.Event
import org.jetbrains.compose.resources.StringResource

data class ResetPasswordScreenContent(
  val titleRes: StringResource = Res.string.set_new_password,
  val errorRes: StringResource? = null,

  val passwordTopTitleRes: StringResource = Res.string.new_password,
  val passwordState: TextFieldState = TextFieldState(),
  val passwordPlaceholderRes: StringResource = Res.string.password,
  val passwordBottomTitleRes: StringResource = Res.string.hint_password,
  val passwordIsError: Boolean = false,
  val passwordIsSecureMode: Boolean = true,

  val primaryButtonTitleRes: StringResource = Res.string.submit,
  val primaryButtonStyle: ButtonStyle = ButtonStyle.PRIMARY,
  val primaryButtonIsEnable: Boolean = false,

  val resetSuccessEvent: Event<StringResource>? = null,
  val navigateToLoginEvent: Event<Unit>? = null,
)
