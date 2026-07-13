package com.plcoding.feature.auth.presentation.screen.register.success

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.ChirError
import com.plcoding.core.designsystem.components.SuccessIcon
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.layout.ResultLayout
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveResultLayout
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.model.ScreenUiState
import com.plcoding.core.presentation.screen.base.BaseScreen
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterSuccessScreen(
  viewModel: RegisterSuccessScreenViewModel = koinViewModel(),
  openLogin: () -> Unit,
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()

  BaseScreen(
    baseUiState = state.baseUiState
  ) {
    Content(
      uiState = state.uiState,
      onAction = {
        when (it) {
          is RegisterSuccessScreenAction.PrimaryButtonClick -> openLogin()
          else -> viewModel.onAction(it)
        }
      },
    )
  }
}

@Composable
private fun Content(
  uiState: RegisterSuccessUiState,
  onAction: (RegisterSuccessScreenAction) -> Unit,
) {
  AdaptiveResultLayout(
    modifier = Modifier.fillMaxSize()
  ) {
    ResultLayout(
      icon = { SuccessIcon() },
      title = stringResource(uiState.titleRes),
      description = uiState.description?.get(),
      primaryButton = {
        Button(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(uiState.primaryButtonTitleRes),
          style = uiState.primaryButtonStyle,
          isLoading = false,
          isEnabled = !uiState.hasOngoingRequest,
          onClick = { onAction(RegisterSuccessScreenAction.PrimaryButtonClick) }
        )
      },
      secondaryButton = {
        Button(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(uiState.secondaryButtonTitleRes),
          style = uiState.secondaryButtonStyle,
          isLoading = false,
          isEnabled = !uiState.hasOngoingRequest,
          onClick = { onAction(RegisterSuccessScreenAction.SecondaryButtonClick) }
        )
        if (uiState.secondaryButtonErrorRes != null) {
          Spacer(Modifier.height(6.dp))
          ChirError(error = stringResource(uiState.secondaryButtonErrorRes))
        }
      }
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenUiState = ScreenUiState(RegisterSuccessUiState())

  Theme(isDarkTheme) {
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
private fun LightPreview() {
  Themed(
    isDarkTheme = false,
  )
}

@Preview
@Composable
private fun DarkPreview() {
  Themed(
    isDarkTheme = true
  )
}
