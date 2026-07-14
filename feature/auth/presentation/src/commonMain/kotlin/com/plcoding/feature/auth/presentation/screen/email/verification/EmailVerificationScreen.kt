package com.plcoding.feature.auth.presentation.screen.email.verification

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
import com.plcoding.core.designsystem.components.SuccessIcon
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.layout.ResultLayout
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveResultLayout
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.core.presentation.model.ScreenUiState
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
    baseUiState = state.baseUiState
  ) {
    Content(
      uiState = state.uiState,
      onAction = { openLogin() },
    )
  }
}

@Composable
private fun Content(
  uiState: EmailVerificationUiState,
  onAction: (EmailVerificationScreenAction) -> Unit,
) {
  AdaptiveResultLayout {
    when (uiState) {
      is EmailVerificationUiState.Failed -> FailedContent(
        uiState,
        onAction
      )
      is EmailVerificationUiState.Loading -> LoadingContent(uiState)
      is EmailVerificationUiState.Success -> SuccessContent(
        uiState,
        onAction
      )
    }
  }
}

@Composable
private fun FailedContent(
  content: EmailVerificationUiState.Failed,
  onAction: (EmailVerificationScreenAction) -> Unit,
) {
  ResultLayout(
    icon = {
      Spacer(Modifier.height(24.dp))
      Icon(
        imageVector = content.imageVector,
        contentDescription = null,
        modifier = Modifier.size(64.dp),
        tint = MaterialTheme.colorScheme.error,
      )
      Spacer(Modifier.height(24.dp))
    },
    title = stringResource(content.titleRes),
    description = stringResource(content.descriptionRes),
    contentOffset = 0.dp,
    primaryButton = {
      Button(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(content.primaryButtonTitleRes),
        style = content.primaryButtonStyle,
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
      text = stringResource(content.titleRes),
      color = MaterialTheme.colorScheme.extended.textSecondary,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodySmall,
    )
  }
}

@Composable
private fun SuccessContent(
  content: EmailVerificationUiState.Success,
  onAction: (EmailVerificationScreenAction) -> Unit,
) {
  ResultLayout(
    icon = { SuccessIcon() },
    title = stringResource(content.titleRes),
    description = stringResource(content.descriptionRes),
    primaryButton = {
      Button(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(content.primaryButtonTitleRes),
        style = content.primaryButtonStyle,
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
      baseUiState = screenUiState.baseUiState
    ) {
      Content(
        uiState = screenUiState.uiState,
        onAction = {}
      )
    }
  }
}

@Preview
@Composable
private fun LoadingPreview() {
  Themed(EmailVerificationUiState.Loading())
}

@Preview
@Composable
private fun FailedPreview() {
  Themed(EmailVerificationUiState.Failed())
}

@Preview
@Composable
private fun SuccessPreview() {
  Themed(EmailVerificationUiState.Success())
}
