package com.plcoding.feature.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.style.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterScreen(
  viewModel: RegisterViewModel
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  RegisterContent(
    state = state,
    onAction = viewModel::onAction
  )
}

@Composable
fun RegisterContent(
  state: RegisterState,
  onAction: (RegisterAction) -> Unit,
) {

}

@Preview
@Composable
private fun RegisterPreview() {
  ChirpTheme {
    RegisterContent(
      state = RegisterState(),
      onAction = {}
    )
  }
}