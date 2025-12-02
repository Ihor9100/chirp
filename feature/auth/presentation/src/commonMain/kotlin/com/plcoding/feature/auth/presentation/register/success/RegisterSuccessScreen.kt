package com.plcoding.feature.auth.presentation.register.success

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.brand.ChirError
import com.plcoding.core.designsystem.components.brand.ChirpSuccessIcon
import com.plcoding.core.designsystem.components.button.ChirpButton
import com.plcoding.core.designsystem.components.layout.ChirpAdaptiveResultLayout
import com.plcoding.core.designsystem.components.layout.ChirpSnackbarLayout
import com.plcoding.core.designsystem.components.layout.ChirpSuccessLayout
import com.plcoding.core.designsystem.style.ChirpTheme
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterSuccessScreen(
  viewModel: RegisterSuccessViewModel = koinViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(state.snackbarEvent) {
    state.snackbarEvent?.consume()?.let {
      snackbarHostState.showSnackbar(getString(it))
    }
  }

  RegisterSuccessContent(
    state = state,
    snackbarHostState = snackbarHostState,
    onAction = viewModel::onAction,
  )
}

@Composable
fun RegisterSuccessContent(
  state: RegisterSuccessState,
  snackbarHostState: SnackbarHostState,
  onAction: (RegisterSuccessAction) -> Unit,
) {
  ChirpSnackbarLayout(
    modifier = Modifier.fillMaxSize(),
    snackbarHostState = snackbarHostState,
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
            enabled = !state.hasOngoingRequest,
            onClick = { onAction(RegisterSuccessAction.PrimaryButtonClick) }
          )
        },
        secondaryButton = {
          ChirpButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(state.secondaryButtonTitleRes),
            style = state.secondaryButtonStyle,
            isLoading = false,
            enabled = !state.hasOngoingRequest,
            onClick = { onAction(RegisterSuccessAction.SecondaryButtonClick) }
          )
          if (state.secondaryButtonErrorRes != null) {
            Spacer(Modifier.height(6.dp))
            ChirError(error = stringResource(state.secondaryButtonErrorRes))
          }
        }
      )
    }
  }
}

@Preview
@Composable
private fun RegisterSuccessPreview() {
  ChirpTheme {
    RegisterSuccessContent(
      state = RegisterSuccessState(),
      snackbarHostState = SnackbarHostState(),
      onAction = {},
    )
  }
}