package com.plcoding.feature.auth.presentation.register.success

import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.account_successfully_created
import chirp.feature.auth.presentation.generated.resources.log_in
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import chirp.feature.auth.presentation.generated.resources.verification_email_sent_to_x
import com.plcoding.core.designsystem.components.button.ChirpButtonStyle
import com.plcoding.core.presentation.utils.TextProvider
import org.jetbrains.compose.resources.StringResource

data class RegisterSuccessState(
  val titleRes: StringResource = Res.string.account_successfully_created,
  val description: TextProvider = TextProvider.Resource(
    id = Res.string.verification_email_sent_to_x,
    args = listOf("test@gmail.com"),
  ),

  val primaryButtonTitleRes: StringResource = Res.string.log_in,
  val primaryButtonStyle: ChirpButtonStyle = ChirpButtonStyle.PRIMARY,
  val primaryButtonIsLoading: Boolean = false,

  val secondaryButtonTitleRes: StringResource = Res.string.resend_verification_email,
  val secondaryButtonStyle: ChirpButtonStyle = ChirpButtonStyle.SECONDARY,
)