package com.plcoding.feature.auth.presentation.register.success

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.brand.ChirpSuccessIcon
import com.plcoding.core.designsystem.components.button.ChirpButton
import com.plcoding.core.designsystem.components.layout.ChirpAdaptiveResultLayout
import com.plcoding.core.designsystem.components.layout.ChirpSuccessLayout
import com.plcoding.core.designsystem.style.ChirpTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterSuccessScreen(
  viewModel: RegisterSuccessViewModel = koinViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  RegisterSuccessContent(
    state = state,
    onAction = viewModel::onAction
  )
}

@Composable
fun RegisterSuccessContent(
  state: RegisterSuccessState,
  onAction: (RegisterSuccessAction) -> Unit,
) {
  ChirpAdaptiveResultLayout(
    modifier = Modifier.fillMaxSize()
  ) {
    ChirpSuccessLayout(
      icon = { ChirpSuccessIcon() },
      title = stringResource(state.titleRes),
      description = state.description.get(),
      primaryButton = {
        ChirpButton(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(state.primaryButtonTitleRes),
          style = state.primaryButtonStyle,
          isLoading = false,
          enabled = true,
          onClick = { onAction(RegisterSuccessAction.PrimaryButtonClick) }
        )
      },
      secondaryButton = {
        ChirpButton(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(state.secondaryButtonTitleRes),
          style = state.secondaryButtonStyle,
          isLoading = false,
          enabled = true,
          onClick = { onAction(RegisterSuccessAction.SecondaryButtonClick) }
        )
      }
    )
  }
}

@Preview
@Composable
private fun RegisterSuccessPreview() {
  ChirpTheme {
    RegisterSuccessContent(
      state = RegisterSuccessState(),
      onAction = {}
    )
  }
}