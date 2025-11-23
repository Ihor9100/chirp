package com.plcoding.feature.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterScreen(
  viewModel: RegisterViewModel = viewModel()
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
    RegisterScreen(
      state = RegisterState(),
      onAction = {}
    )
  }
}