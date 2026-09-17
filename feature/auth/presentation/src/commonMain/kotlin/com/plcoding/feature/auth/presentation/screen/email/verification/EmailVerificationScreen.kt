package com.plcoding.feature.auth.presentation.screen.email.verification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.close
import chirp.feature.auth.presentation.generated.resources.email_verified_failed
import chirp.feature.auth.presentation.generated.resources.email_verified_failed_description
import chirp.feature.auth.presentation.generated.resources.email_verified_successfully
import chirp.feature.auth.presentation.generated.resources.email_verified_successfully_description
import chirp.feature.auth.presentation.generated.resources.log_in
import chirp.feature.auth.presentation.generated.resources.verifying_account
import com.plcoding.core.designsystem.components.SuccessIcon
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.designsystem.components.layout.ResultLayout
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveResultLayout
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.presentation.model.ScreenUiState
import com.plcoding.core.presentation.screen.base.BaseScreen
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailVerificationScreen(
  viewModel: EmailVerificationScreenViewModel = koinViewModel(),
  openLogin: () -> Unit,
) {
  val state by viewModel.screenUiState.collectAsStateWithLifecycle()

  BaseScreen(
    baseUiState = state.baseUiState,
    backgroundColor = MaterialTheme.colorScheme.background,
    isSafeDrawing = false,
  ) {
    EmailVerificationScreenContent(
      uiState = state.uiState,
      onAction = { openLogin() },
    )
  }
}

@Composable
fun EmailVerificationScreenContent(
  uiState: EmailVerificationUiState,
  onAction: (EmailVerificationScreenAction) -> Unit,
) {
  AdaptiveResultLayout(
    modifier = Modifier.testTag(EmailVerificationScreenTestTag.SCREEN.value)
  ) {
    when (uiState) {
      is EmailVerificationUiState.Failed -> FailedContent(onAction)
      is EmailVerificationUiState.Loading -> LoadingContent(uiState)
      is EmailVerificationUiState.Success -> SuccessContent(onAction)
    }
  }
}

@Composable
private fun FailedContent(
  onAction: (EmailVerificationScreenAction) -> Unit,
) {
  ResultLayout(
    icon = {
      Spacer(Modifier.height(24.dp))
      Icon(
        imageVector = Icons.Default.Close,
        contentDescription = null,
        modifier = Modifier.size(64.dp),
        tint = MaterialTheme.colorScheme.error,
      )
      Spacer(Modifier.height(24.dp))
    },
    title = stringResource(Res.string.email_verified_failed),
    description = stringResource(Res.string.email_verified_failed_description),
    contentOffset = 0.dp,
    primaryButton = {
      Button(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.close),
        style = ButtonStyle.SECONDARY,
        onClick = { onAction(EmailVerificationScreenAction.OnCloseClick) }
      )
    },
    secondaryButton = null,
  )
}

@Composable
private fun LoadingContent(
  content: EmailVerificationUiState.Loading,
) {
  Column(
    modifier = Modifier.heightIn(min = 200.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(Modifier.height(24.dp))
    CircularProgressIndicator(
      modifier = Modifier.size(64.dp),
      color = MaterialTheme.colorScheme.primary,
    )
    Spacer(Modifier.height(24.dp))
    Text(
      text = stringResource(Res.string.verifying_account),
      color = MaterialTheme.colorScheme.extended.textSecondary,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodySmall,
    )
  }
}

@Composable
private fun SuccessContent(
  onAction: (EmailVerificationScreenAction) -> Unit,
) {
  ResultLayout(
    icon = { SuccessIcon() },
    title = stringResource(Res.string.email_verified_successfully),
    description = stringResource(Res.string.email_verified_successfully_description),
    primaryButton = {
      Button(
        modifier = Modifier
          .testTag(EmailVerificationScreenTestTag.LOG_IN_BUTTON.value)
          .fillMaxWidth(),
        text = stringResource(Res.string.log_in),
        style = ButtonStyle.PRIMARY,
        onClick = { onAction(EmailVerificationScreenAction.OnLogInClick) }
      )
    },
    secondaryButton = null,
  )
}

@Composable
private fun Themed(
  content: EmailVerificationUiState,
) {
  val screenUiState = ScreenUiState(content)

  Theme(isDarkMode = true) {
    BaseScreen(
      baseUiState = screenUiState.baseUiState,
      backgroundColor = MaterialTheme.colorScheme.background,
    ) {
      EmailVerificationScreenContent(
        uiState = screenUiState.uiState,
        onAction = {}
      )
    }
  }
}

@Preview
@Composable
private fun LoadingPreview() {
  Themed(EmailVerificationUiState.Loading)
}

@Preview
@Composable
private fun FailedPreview() {
  Themed(EmailVerificationUiState.Failed)
}

@Preview
@Composable
private fun SuccessPreview() {
  Themed(EmailVerificationUiState.Success)
}

enum class EmailVerificationScreenTestTag(val value: String) {
  SCREEN("email_verification_screen"),
  LOG_IN_BUTTON("email_verification_screen_primary_button"),
}