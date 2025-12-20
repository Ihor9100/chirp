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
import com.plcoding.core.designsystem.components.brand.ChirpLogo
import com.plcoding.core.designsystem.components.button.ChirpButton
import com.plcoding.core.designsystem.components.layout.ChirAdaptiveFormLayout
import com.plcoding.core.designsystem.components.layout.ChirpSnackbarLayout
import com.plcoding.core.designsystem.components.textfields.ChirpTextFieldPassword
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.core.presentation.screen.base.BaseScreenContent
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
  val state by viewModel.state.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  rememberCoroutineScope().launch {
    state.content.resetSuccessEvent?.consumeAsync {
      val result = snackbarHostState.showSnackbar(getString(it))
      viewModel.onAction(ResetPasswordScreenAction.OnSnackbarDisappeared(result))
    }
  }

  state.content.navigateToLoginEvent?.run(openLogin)

  BaseScreenContent(
    baseContent = state.baseContent
  ) {
    ResetPasswordScreenContent(
      content = state.content,
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
  ChirpSnackbarLayout(
    modifier = Modifier.fillMaxSize(),
    snackbarHostState = snackbarHostState,
  ) {
    ChirAdaptiveFormLayout(
      modifier = Modifier.fillMaxSize(),
      logo = { ChirpLogo() },
      title = stringResource(content.titleRes),
      error = content.errorRes?.let { stringResource(it) },
    ) {
      ChirpTextFieldPassword(
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
      ChirpButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(content.primaryButtonTitleRes),
        style = content.primaryButtonStyle,
        enabled = content.primaryButtonIsEnable,
        onClick = { onAction(ResetPasswordScreenAction.OnPrimaryButtonClick) }
      )
    }
  }
}

@Preview
@Composable
private fun ResetPasswordScreenPreview() {
  ChirpTheme {
    ResetPasswordScreenContent(
      content = ResetPasswordScreenContent(),
      snackbarHostState = SnackbarHostState(),
      onAction = {}
    )
  }
}