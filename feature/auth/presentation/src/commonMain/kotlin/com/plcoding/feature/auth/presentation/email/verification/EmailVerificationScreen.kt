package com.plcoding.feature.auth.presentation.email.verification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.brand.ChirpSuccessIcon
import com.plcoding.core.designsystem.components.button.ChirpButton
import com.plcoding.core.designsystem.components.layout.ChirpAdaptiveResultLayout
import com.plcoding.core.designsystem.components.layout.ChirpResultLayout
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailVerificationScreen(
  viewModel: EmailVerificationViewModel = koinViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  EmailVerificationContent(
    state = state,
    onAction = viewModel::onAction
  )
}

@Composable
fun EmailVerificationContent(
  state: EmailVerificationState,
  onAction: (EmailVerificationAction) -> Unit,
) {
  ChirpAdaptiveResultLayout {
    when (state) {
      is EmailVerificationState.Failed -> EmailVerificationFailedContent(state, onAction)
      is EmailVerificationState.Loading -> EmailVerificationLoadingContent(state)
      is EmailVerificationState.Success -> EmailVerificationSuccessContent(state, onAction)
    }
  }
}

@Composable
fun EmailVerificationFailedContent(
  state: EmailVerificationState.Failed,
  onAction: (EmailVerificationAction) -> Unit,
) {
  ChirpResultLayout(
    icon = {
      Spacer(Modifier.height(24.dp))
      Icon(
        imageVector = state.imageVector,
        contentDescription = null,
        modifier = Modifier.size(64.dp),
        tint = MaterialTheme.colorScheme.error,
      )
      Spacer(Modifier.height(24.dp))
    },
    title = stringResource(state.titleRes),
    description = stringResource(state.descriptionRes),
    contentOffset = 0.dp,
    primaryButton = {
      ChirpButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(state.primaryButtonTitleRes),
        style = state.primaryButtonStyle,
        onClick = { onAction(EmailVerificationAction.OnCloseClick) }
      )
    },
    secondaryButton = null,
  )
}

@Composable
fun EmailVerificationLoadingContent(
  state: EmailVerificationState.Loading,
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
      text = stringResource(state.titleRes),
      color = MaterialTheme.colorScheme.extended.textSecondary,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodySmall,
    )
  }
}

@Composable
fun EmailVerificationSuccessContent(
  state: EmailVerificationState.Success,
  onAction: (EmailVerificationAction) -> Unit,
) {
  ChirpResultLayout(
    icon = { ChirpSuccessIcon() },
    title = stringResource(state.titleRes),
    description = stringResource(state.descriptionRes),
    primaryButton = {
      ChirpButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(state.primaryButtonTitleRes),
        style = state.primaryButtonStyle,
        onClick = { onAction(EmailVerificationAction.OnLogInClick) }
      )
    },
    secondaryButton = null,
  )
}

@Composable
private fun EmailVerificationThemed(
  state: EmailVerificationState,
) {
  ChirpTheme {
    EmailVerificationContent(
      state = state,
      onAction = {}
    )
  }
}

@Preview
@Composable
private fun EmailVerificationLoadingPreview() {
  EmailVerificationThemed(EmailVerificationState.Loading())
}

@Preview
@Composable
private fun EmailVerificationFailedLoadingPreview() {
  EmailVerificationThemed(EmailVerificationState.Failed())
}

@Preview
@Composable
private fun EmailVerificationFailedSuccessPreview() {
  EmailVerificationThemed(EmailVerificationState.Success())
}