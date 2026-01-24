package com.plcoding.feature.auth.presentation.screen.forgot.password

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.AppLogoPc
import com.plcoding.core.designsystem.components.button.ButtonPc
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveFormLayout
import com.plcoding.core.designsystem.components.layout.SnackbarLayout
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreenContent
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ForgotPasswordScreen(
  viewModel: ForgotPasswordScreenViewModel = koinViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  rememberCoroutineScope().launch {
    state.content.snackbarEvent?.consumeAsync {
      snackbarHostState.showSnackbar(getString(it))
    }
  }

  BaseScreenContent(
    baseContent = state.baseContent
  ) {
    ForgotPasswordScreenContent(
      content = state.content,
      snackbarHostState = snackbarHostState,
      onAction = viewModel::onAction
    )
  }
}

@Composable
fun ForgotPasswordScreenContent(
  content: ForgotPasswordScreenContent,
  snackbarHostState: SnackbarHostState,
  onAction: (ForgotPasswordScreenAction) -> Unit,
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
      TextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(content.emailTopTitleRes),
        textFieldState = content.emailState,
        inputPlaceholder = stringResource(content.emailPlaceholderRes),
        bottomTitle = null,
        keyboardType = KeyboardType.Email,
      )
      Spacer(Modifier.height(32.dp))
      ButtonPc(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(content.primaryButtonTitleRes),
        style = content.primaryButtonPcStyle,
        isEnabled = content.primaryButtonIsEnable,
        onClick = { onAction(ForgotPasswordScreenAction.OnSubmitClick) },
      )
    }
  }
}

@Preview
@Composable
private fun ForgotPasswordScreenPreview() {
  Theme {
    ForgotPasswordScreenContent(
      content = ForgotPasswordScreenContent(),
      snackbarHostState = SnackbarHostState(),
      onAction = {},
    )
  }
}