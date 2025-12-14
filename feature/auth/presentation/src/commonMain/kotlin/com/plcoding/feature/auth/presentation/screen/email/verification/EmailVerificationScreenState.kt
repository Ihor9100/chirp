package com.plcoding.feature.auth.presentation.screen.email.verification

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.graphics.vector.ImageVector
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.close
import chirp.feature.auth.presentation.generated.resources.email_verified_failed
import chirp.feature.auth.presentation.generated.resources.email_verified_failed_description
import chirp.feature.auth.presentation.generated.resources.email_verified_successfully
import chirp.feature.auth.presentation.generated.resources.email_verified_successfully_description
import chirp.feature.auth.presentation.generated.resources.log_in
import chirp.feature.auth.presentation.generated.resources.verifying_account
import com.plcoding.core.designsystem.components.button.ChirpButtonStyle
import com.plcoding.core.presentation.screen.base.BaseScreenState
import org.jetbrains.compose.resources.StringResource

sealed interface EmailVerificationScreenState: BaseScreenState<EmailVerificationScreenState> {

  data class Loading(
    val titleRes: StringResource = Res.string.verifying_account,
  ) : EmailVerificationScreenState

  data class Failed(
    val imageVector: ImageVector = Icons.Default.Close,
    val titleRes: StringResource = Res.string.email_verified_failed,
    val descriptionRes: StringResource = Res.string.email_verified_failed_description,
    val primaryButtonTitleRes: StringResource = Res.string.close,
    val primaryButtonStyle: ChirpButtonStyle = ChirpButtonStyle.SECONDARY,
  ) : EmailVerificationScreenState

  data class Success(
    val titleRes: StringResource = Res.string.email_verified_successfully,
    val descriptionRes: StringResource = Res.string.email_verified_successfully_description,
    val primaryButtonTitleRes: StringResource = Res.string.log_in,
    val primaryButtonStyle: ChirpButtonStyle = ChirpButtonStyle.PRIMARY,
  ) : EmailVerificationScreenState
}
