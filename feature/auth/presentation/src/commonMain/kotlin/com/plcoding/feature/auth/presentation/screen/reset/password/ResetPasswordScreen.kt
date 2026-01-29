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
import com.plcoding.core.designsystem.components.AppLogoPc
import com.plcoding.core.designsystem.components.button.ButtonPc
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveFormLayout
import com.plcoding.core.designsystem.components.textfields.TextFieldPassword
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.core.presentation.screen.model.ScreenStatePm
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResetPasswordScreen(
  viewModel: ResetPasswordScreenViewModel = koinViewModel(),
  openLogin: () -> Unit,
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()
  state.contentPm.navigateToLoginEvent?.run(openLogin)

  BaseScreen(
    baseContentPm = state.baseContentPm
  ) {
    Content(
      contentPm = state.contentPm,
      onAction = viewModel::onAction
    )
  }
}

@Composable
private fun Content(
  contentPm: ResetPasswordScreenContentPm,
  onAction: (ResetPasswordScreenAction) -> Unit,
) {
  AdaptiveFormLayout(
    modifier = Modifier.fillMaxSize(),
    logo = { AppLogoPc() },
    title = stringResource(contentPm.titleRes),
    error = contentPm.errorRes?.let { stringResource(it) },
  ) {
    TextFieldPassword(
      modifier = Modifier.fillMaxWidth(),
      topTitle = stringResource(contentPm.passwordTopTitleRes),
      textFieldState = contentPm.passwordState,
      inputPlaceholder = stringResource(contentPm.passwordPlaceholderRes),
      bottomTitle = stringResource(contentPm.passwordBottomTitleRes),
      isError = contentPm.passwordIsError,
      isSecureMode = contentPm.passwordIsSecureMode,
      onSecureToggleClick = { onAction(ResetPasswordScreenAction.OnTextFieldSecureToggleClick) }
    )
    Spacer(Modifier.height(32.dp))
    ButtonPc(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(contentPm.primaryButtonTitleRes),
      style = contentPm.primaryButtonPcStyle,
      isEnabled = contentPm.primaryButtonIsEnable,
      onClick = { onAction(ResetPasswordScreenAction.OnPrimaryButtonClick) }
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenStatePm = ScreenStatePm(ResetPasswordScreenContentPm())

  Theme(isDarkTheme) {
    BaseScreen(
      baseContentPm = screenStatePm.baseContentPm
    ) {
      Content(
        contentPm = screenStatePm.contentPm,
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
