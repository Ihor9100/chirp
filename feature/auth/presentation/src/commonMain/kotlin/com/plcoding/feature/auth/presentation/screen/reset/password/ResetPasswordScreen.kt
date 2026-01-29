package com.plcoding.feature.auth.presentation.screen.reset.password

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.AppLogoPc
import com.plcoding.core.designsystem.components.button.ButtonPc
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveFormLayout
import com.plcoding.core.designsystem.components.layout.SnackbarLayout
import com.plcoding.core.designsystem.components.textfields.TextFieldPassword
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreen
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResetPasswordScreen(
  viewModel: ResetPasswordScreenViewModel = koinViewModel(),
  openLogin: () -> Unit,
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  rememberCoroutineScope().launch {
    state.contentPm.resetSuccessEvent?.consumeAsync {
      val result = snackbarHostState.showSnackbar(getString(it))
      viewModel.onAction(ResetPasswordScreenAction.OnSnackbarDisappeared(result))
    }
  }

  state.contentPm.navigateToLoginEvent?.run(openLogin)

  BaseScreen(
    baseContentPm = state.baseContentPm
  ) {
    ResetPasswordScreenContent(
      content = state.contentPm,
      snackbarHostState = snackbarHostState,
      onAction = viewModel::onAction
    )
  }
}

@Composable
fun ResetPasswordScreenContent(
  content: ResetPasswordScreenContent,
  snackbarHostState: SnackbarHostState,
  onAction: (ResetPasswordScreenAction) -> Unit,
) {
  SnackbarLayout(
    modifier = Modifier.fillMaxSize(),
    snackbarHostState = snackbarHostState,
  ) {
    AdaptiveFormLayout(
      modifier = Modifier.fillMaxSize(),
      logo = { AppLogoPc() },
      title = stringResource(content.titleRes),
      error = content.errorRes?.let { stringResource(it) },
    ) {
      TextFieldPassword(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(content.passwordTopTitleRes),
        textFieldState = content.passwordState,
        inputPlaceholder = stringResource(content.passwordPlaceholderRes),
        bottomTitle =  stringResource(content.passwordBottomTitleRes),
        isError = content.passwordIsError,
        isSecureMode = content.passwordIsSecureMode,
        onSecureToggleClick = { onAction(ResetPasswordScreenAction.OnTextFieldSecureToggleClick) }
      )
      Spacer(Modifier.height(32.dp))
      ButtonPc(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(content.primaryButtonTitleRes),
        style = content.primaryButtonPcStyle,
        isEnabled = content.primaryButtonIsEnable,
        onClick = { onAction(ResetPasswordScreenAction.OnPrimaryButtonClick) }
      )
    }
  }
}

@Preview
@Composable
private fun ResetPasswordScreenPreview() {
  Theme {
    ResetPasswordScreenContent(
      content = ResetPasswordScreenContent(),
      snackbarHostState = SnackbarHostState(),
      onAction = {}
    )
  }
}