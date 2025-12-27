package com.plcoding.feature.auth.presentation.screen.register.success

import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.account_successfully_created
import chirp.feature.auth.presentation.generated.resources.log_in
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.utils.TextProvider
import org.jetbrains.compose.resources.StringResource

data class RegisterSuccessScreenContent(
  val titleRes: StringResource = Res.string.account_successfully_created,
  val description: TextProvider? = null,

  val primaryButtonTitleRes: StringResource = Res.string.log_in,
  val primaryButtonStyle: ButtonStyle = ButtonStyle.PRIMARY,
  val primaryButtonIsLoading: Boolean = false,

  val secondaryButtonTitleRes: StringResource = Res.string.resend_verification_email,
  val secondaryButtonStyle: ButtonStyle = ButtonStyle.SECONDARY,
  val secondaryButtonErrorRes: StringResource? = null,

  val snackbarEvent: Event<StringResource>? = null,
  val hasOngoingRequest: Boolean = false
)