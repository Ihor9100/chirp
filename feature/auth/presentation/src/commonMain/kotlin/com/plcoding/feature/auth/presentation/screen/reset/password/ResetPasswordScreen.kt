package com.plcoding.feature.auth.presentation.screen.reset.password

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.AppLogo
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveFormLayout
import com.plcoding.core.designsystem.components.textfields.TextFieldPassword
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.core.presentation.model.ScreenUiState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResetPasswordScreen(
  viewModel: ResetPasswordScreenViewModel = koinViewModel(),
  openLogin: () -> Unit,
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()
  state.uiState.navigateToLoginEvent?.run(openLogin)

  BaseScreen(
    baseUiState = state.baseUiState
  ) {
    Content(
      uiState = state.uiState,
      onAction = viewModel::onAction
    )
  }
}

@Composable
private fun Content(
  uiState: ResetPasswordUiState,
  onAction: (ResetPasswordScreenAction) -> Unit,
) {
  AdaptiveFormLayout(
    modifier = Modifier.fillMaxSize(),
    logo = { AppLogo() },
    title = stringResource(uiState.titleRes),
    error = uiState.errorRes?.let { stringResource(it) },
  ) {
    TextFieldPassword(
      modifier = Modifier.fillMaxWidth(),
      topTitle = stringResource(uiState.passwordTopTitleRes),
      textFieldState = uiState.passwordState,
      inputPlaceholder = stringResource(uiState.passwordPlaceholderRes),
      bottomTitle = stringResource(uiState.passwordBottomTitleRes),
      isError = uiState.passwordIsError,
      isSecureMode = uiState.passwordIsSecureMode,
      onSecureToggleClick = { onAction(ResetPasswordScreenAction.OnTextFieldSecureToggleClick) }
    )
    Spacer(Modifier.height(32.dp))
    Button(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(uiState.primaryButtonTitleRes),
      style = uiState.primaryButtonStyle,
      isEnabled = uiState.primaryButtonIsEnable,
      onClick = { onAction(ResetPasswordScreenAction.OnPrimaryButtonClick) }
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenUiState = ScreenUiState(ResetPasswordUiState())

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
