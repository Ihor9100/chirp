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
import com.plcoding.core.designsystem.components.layout.AdaptiveResultLayout
import com.plcoding.core.designsystem.components.layout.ResultLayout
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.presentation.screen.base.BaseScreenContent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailVerificationScreen(
  viewModel: EmailVerificationScreenViewModel = koinViewModel(),
  openLogin: () -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  BaseScreenContent(
    baseContent = state.baseContent
  ) {
    EmailVerificationScreenContent(
      content = state.content,
      onAction = { openLogin() },
    )
  }
}

@Composable
fun EmailVerificationScreenContent(
  content: EmailVerificationScreenContent,
  onAction: (EmailVerificationScreenAction) -> Unit,
) {
  AdaptiveResultLayout {
    when (content) {
      is EmailVerificationScreenContent.Failed -> EmailVerificationScreenFailedContent(
        content,
        onAction
      )
      is EmailVerificationScreenContent.Loading -> EmailVerificationScreenLoadingContent(content)
      is EmailVerificationScreenContent.Success -> EmailVerificationScreenSuccessContent(
        content,
        onAction
      )
    }
  }
}

@Composable
fun EmailVerificationScreenFailedContent(
  content: EmailVerificationScreenContent.Failed,
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
fun EmailVerificationScreenLoadingContent(
  content: EmailVerificationScreenContent.Loading,
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
fun EmailVerificationScreenSuccessContent(
  content: EmailVerificationScreenContent.Success,
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
private fun EmailVerificationScreenThemed(
  content: EmailVerificationScreenContent,
) {
  Theme {
    EmailVerificationScreenContent(
      content = content,
      onAction = {}
    )
  }
}

@Preview
@Composable
private fun EmailVerificationScreenLoadingPreview() {
  EmailVerificationScreenThemed(EmailVerificationScreenContent.Loading())
}

@Preview
@Composable
private fun EmailVerificationScreenFailedLoadingPreview() {
  EmailVerificationScreenThemed(EmailVerificationScreenContent.Failed())
}

@Preview
@Composable
private fun EmailVerificationScreenFailedSuccessPreview() {
  EmailVerificationScreenThemed(EmailVerificationScreenContent.Success())
}