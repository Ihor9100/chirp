package com.plcoding.feature.auth.presentation.screen.register.success

import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.account_successfully_created
import chirp.feature.auth.presentation.generated.resources.log_in
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import com.plcoding.core.designsystem.components.button.ButtonPcStyle
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.utils.TextProvider
import org.jetbrains.compose.resources.StringResource

data class RegisterSuccessScreenContentPm(
  val titleRes: StringResource = Res.string.account_successfully_created,
  val description: TextProvider? = null,

  val primaryButtonTitleRes: StringResource = Res.string.log_in,
  val primaryButtonPcStyle: ButtonPcStyle = ButtonPcStyle.PRIMARY,
  val primaryButtonIsLoading: Boolean = false,

  val secondaryButtonTitleRes: StringResource = Res.string.resend_verification_email,
  val secondaryButtonPcStyle: ButtonPcStyle = ButtonPcStyle.SECONDARY,
  val secondaryButtonErrorRes: StringResource? = null,

  val hasOngoingRequest: Boolean = false
)