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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.core.presentation.generated.resources.chirp
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.create_account
import chirp.feature.auth.presentation.generated.resources.forgot_password
import chirp.feature.auth.presentation.generated.resources.log_in
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.username_or_email
import chirp.feature.auth.presentation.generated.resources.welcome_back
import com.plcoding.core.designsystem.components.AppLogo
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveFormLayout
import com.plcoding.core.designsystem.components.textfields.TextFieldPassword
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.model.ScreenUiState
import com.plcoding.core.presentation.screen.base.BaseScreen
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import chirp.core.presentation.generated.resources.Res as CoreRes

@Composable
fun LoginScreen(
  viewModel: LoginScreenViewModel = koinViewModel(),
  openChat: () -> Unit,
  openForgotPassword: () -> Unit,
  openRegisterScreen: () -> Unit,
) {
  val state by viewModel.screenUiState.collectAsStateWithLifecycle()
  state.uiState.logInSuccessEvent?.run(openChat)

  BaseScreen(
    baseUiState = state.baseUiState,
    backgroundColor = MaterialTheme.colorScheme.background,
    isSafeDrawing = false,
  ) {
    LoginScreenContent(
      uiState = state.uiState,
      onAction = {
        when (it) {
          is LoginScreenAction.OnForgotPasswordClick -> openForgotPassword()
          is LoginScreenAction.OnRegisterClick -> openRegisterScreen()
          else -> viewModel.onAction(it)
        }
      }
    )
  }
}

@Composable
fun LoginScreenContent(
  uiState: LoginUiState,
  onAction: (LoginScreenAction) -> Unit,
) {
  AdaptiveFormLayout(
    modifier = Modifier
      .testTag(LoginScreenTestTag.SCREEN.value)
      .fillMaxSize(),
    logo = { AppLogo() },
    title = stringResource(Res.string.welcome_back),
    error = uiState.errorRes?.let { stringResource(it) },
    form = {
      TextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(Res.string.username_or_email),
        textFieldState = uiState.emailState,
        inputPlaceholder = stringResource(CoreRes.string.chirp),
        bottomTitle = null,
        keyboardType = KeyboardType.Text,
      )
      Spacer(Modifier.height(20.dp))
      TextFieldPassword(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(Res.string.password),
        textFieldState = uiState.passwordState,
        inputPlaceholder = stringResource(Res.string.password),
        bottomTitle = null,
        isSecureMode = uiState.passwordIsSecureMode,
        onSecureToggleClick = { onAction(LoginScreenAction.OnPasswordSecureToggleClick) }
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
      Button(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.log_in),
        style = ButtonStyle.PRIMARY,
        isEnabled = uiState.primaryButtonIsEnable,
        onClick = { onAction(LoginScreenAction.OnLoginClick) }
      )
      Button(
        modifier = Modifier
          .testTag(LoginScreenTestTag.REGISTER_BUTTON.value)
          .fillMaxWidth(),
        text = stringResource(Res.string.create_account),
        style = ButtonStyle.SECONDARY,
        onClick = { onAction(LoginScreenAction.OnRegisterClick) }
      )
    },
  )
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenUiState = ScreenUiState(LoginUiState())

  Theme(isDarkTheme) {
    BaseScreen(
      baseUiState = screenUiState.baseUiState,
      backgroundColor = MaterialTheme.colorScheme.background,
    ) {
      LoginScreenContent(
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

enum class LoginScreenTestTag(val value: String) {
  SCREEN("login_screen"),
  LOGIN_BUTTON("login_screen_login_button"),
  REGISTER_BUTTON("login_screen_register_button"),
}
