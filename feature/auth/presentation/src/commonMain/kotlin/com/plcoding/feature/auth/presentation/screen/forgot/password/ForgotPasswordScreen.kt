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
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.core.presentation.screen.model.ScreenStatePm
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ForgotPasswordScreen(
  viewModel: ForgotPasswordScreenViewModel = koinViewModel()
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  rememberCoroutineScope().launch {
    state.contentPm.snackbarEvent?.consumeAsync {
      snackbarHostState.showSnackbar(getString(it))
    }
  }

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
  contentPm: ForgotPasswordScreenContentPm,
  onAction: (ForgotPasswordScreenAction) -> Unit,
) {
    AdaptiveFormLayout(
      modifier = Modifier.fillMaxSize(),
      logo = { AppLogoPc() },
      title = stringResource(contentPm.titleRes),
      error = contentPm.errorRes?.let { stringResource(it) },
    ) {
      TextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(contentPm.emailTopTitleRes),
        textFieldState = contentPm.emailState,
        inputPlaceholder = stringResource(contentPm.emailPlaceholderRes),
        bottomTitle = null,
        keyboardType = KeyboardType.Email,
      )
      Spacer(Modifier.height(32.dp))
      ButtonPc(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(contentPm.primaryButtonTitleRes),
        style = contentPm.primaryButtonPcStyle,
        isEnabled = contentPm.primaryButtonIsEnable,
        onClick = { onAction(ForgotPasswordScreenAction.OnSubmitClick) },
      )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenStatePm = ScreenStatePm(ForgotPasswordScreenContentPm())

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

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkTheme = false,
  )
}

@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkTheme = true,
  )
}
