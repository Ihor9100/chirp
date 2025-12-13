package com.plcoding.feature.auth.presentation.screen.forgot.password

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.core.designsystem.style.ChirpTheme

@Composable
fun ForgotPasswordScreen(
  viewModel: ForgotPasswordViewModel = viewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  ForgotPasswordContent(
    state = state,
    onAction = viewModel::onAction
  )
}

@Composable
fun ForgotPasswordContent(
  state: ForgotPasswordState,
  onAction: (ForgotPasswordAction) -> Unit,
) {

}

@Preview
@Composable
private fun ForgotPasswordPreview() {
  ChirpTheme {
    ForgotPasswordContent(
      state = ForgotPasswordState(),
      onAction = {}
    )
  }
}