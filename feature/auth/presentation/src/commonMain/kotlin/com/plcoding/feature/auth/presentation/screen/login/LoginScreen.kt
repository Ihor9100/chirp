package com.plcoding.feature.auth.presentation.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.forgot_password
import com.plcoding.core.designsystem.components.AppLogoPc
import com.plcoding.core.designsystem.components.button.ButtonPc
import com.plcoding.core.designsystem.components.button.ButtonPcStyle
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveFormLayout
import com.plcoding.core.designsystem.components.textfields.TextFieldPassword
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.core.presentation.screen.model.ScreenStatePm
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
  viewModel: LoginScreenViewModel = koinViewModel(),
  openChat: () -> Unit,
  openForgotPassword: () -> Unit,
  openRegisterScreen: () -> Unit,
) {
  val state by viewModel.screenState.collectAsStateWithLifecycle()
  state.contentPm.logInSuccessEvent?.run(openChat)

  BaseScreen(
    baseContentPm = state.baseContentPm,
  ) {
    Content(
      contentPm = state.contentPm,
      onAction = {
        when (it) {
          is LoginScreenAction.OnForgotPasswordClick -> openForgotPassword()
          is LoginScreenAction.OnSecondaryButtonClick -> openRegisterScreen()
          else -> viewModel.onAction(it)
        }
      }
    )
  }
}

@Composable
private fun Content(
  contentPm: LoginScreenContentPm,
  onAction: (LoginScreenAction) -> Unit,
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
      keyboardType = KeyboardType.Text,
    )
    Spacer(Modifier.height(20.dp))
    TextFieldPassword(
      modifier = Modifier.fillMaxWidth(),
      topTitle = stringResource(contentPm.passwordTopTitleRes),
      textFieldState = contentPm.passwordState,
      inputPlaceholder = stringResource(contentPm.passwordPlaceholderRes),
      bottomTitle = null,
      isSecureMode = contentPm.passwordIsSecureMode,
      onSecureToggleClick = { onAction(LoginScreenAction.OnTextFieldSecureToggleClick) }
    )
    Spacer(Modifier.height(20.dp))
    Text(
      modifier = Modifier
        .align(Alignment.End)
        .clickable { onAction(LoginScreenAction.OnForgotPasswordClick) },
      text = stringResource(Res.string.forgot_password),
      color = MaterialTheme.colorScheme.tertiary,
      style = MaterialTheme.typography.bodySmall,
    )
    Spacer(Modifier.height(32.dp))
    ButtonPc(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(contentPm.primaryButtonTitleRes),
      style = ButtonPcStyle.PRIMARY,
      isEnabled = contentPm.primaryButtonIsEnable,
      onClick = { onAction(LoginScreenAction.OnPrimaryButtonClick) }
    )
    ButtonPc(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(contentPm.secondaryButtonTitleRes),
      style = ButtonPcStyle.SECONDARY,
      onClick = { onAction(LoginScreenAction.OnSecondaryButtonClick) }
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenStatePm = ScreenStatePm(LoginScreenContentPm())

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