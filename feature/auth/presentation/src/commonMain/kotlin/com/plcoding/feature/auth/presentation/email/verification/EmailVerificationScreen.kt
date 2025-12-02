package com.plcoding.feature.auth.presentation.email.verification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.core.designsystem.style.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmailVerificationScreen(
  viewModel: EmailVerificationViewModel = viewModel()
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
  when (state) {
    is EmailVerificationState.Failed -> TODO()
    is EmailVerificationState.Loading -> TODO()
    is EmailVerificationState.Success -> TODO()
  }
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