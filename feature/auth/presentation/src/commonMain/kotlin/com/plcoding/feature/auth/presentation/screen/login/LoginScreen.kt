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
import com.plcoding.core.designsystem.components.brand.ChirpLogo
import com.plcoding.core.designsystem.components.button.ChirpButton
import com.plcoding.core.designsystem.components.button.ChirpButtonStyle
import com.plcoding.core.designsystem.components.layout.ChirAdaptiveFormLayout
import com.plcoding.core.designsystem.components.textfields.ChirpTextFieldPassword
import com.plcoding.core.designsystem.components.textfields.ChirpTextFieldPlain
import com.plcoding.core.designsystem.style.ChirpTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreenScreen(
  viewModel: LoginViewModel = koinViewModel(),
  openChat: () -> Unit,
  openForgotPassword: () -> Unit,
  openRegisterScreen: () -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  state.logInSuccessEvent?.run(openChat)

  LoginScreenContent(
    state = state,
    onAction = {
      when (it) {
        is LoginAction.OnForgotPasswordClick -> openForgotPassword()
        is LoginAction.OnSecondaryButtonClick -> openRegisterScreen()
        else -> viewModel.onAction(it)
      }
    }
  )
}

@Composable
fun LoginScreenContent(
  state: LoginState,
  onAction: (LoginAction) -> Unit,
) {
  ChirAdaptiveFormLayout(
    modifier = Modifier.fillMaxSize(),
    logo = { ChirpLogo() },
    title = stringResource(state.titleRes),
    error = state.errorRes?.let { stringResource(it) },
  ) {
    ChirpTextFieldPlain(
      modifier = Modifier.fillMaxWidth(),
      topTitle = stringResource(state.emailTopTitleRes),
      textFieldState = state.emailState,
      inputPlaceholder = stringResource(state.emailPlaceholderRes),
      bottomTitle = null,
      keyboardType = KeyboardType.Text,
    )
    Spacer(Modifier.height(20.dp))
    ChirpTextFieldPassword(
      modifier = Modifier.fillMaxWidth(),
      topTitle = stringResource(state.passwordTopTitleRes),
      textFieldState = state.passwordState,
      inputPlaceholder = stringResource(state.passwordPlaceholderRes),
      bottomTitle = null,
      isSecureMode = state.passwordIsSecureMode,
      onSecureToggleClick = { onAction(LoginAction.OnTextFieldSecureToggleClick) }
    )
    Spacer(Modifier.height(20.dp))
    Text(
      modifier = Modifier
        .align(Alignment.End)
        .clickable { onAction(LoginAction.OnForgotPasswordClick) },
      text = stringResource(Res.string.forgot_password),
      color = MaterialTheme.colorScheme.tertiary,
      style = MaterialTheme.typography.bodySmall,
    )
    Spacer(Modifier.height(32.dp))
    ChirpButton(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(state.primaryButtonTitleRes),
      style = ChirpButtonStyle.PRIMARY,
      isLoading = state.hasOngoingRequest,
      enabled = state.primaryButtonIsEnable,
      onClick = { onAction(LoginAction.OnPrimaryButtonClick) }
    )
    ChirpButton(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(state.secondaryButtonTitleRes),
      style = ChirpButtonStyle.SECONDARY,
      onClick = { onAction(LoginAction.OnSecondaryButtonClick) }
    )
  }
}

@Preview
@Composable
private fun LoginScreenPreview() {
  ChirpTheme {
    LoginScreenContent(
      state = LoginState(),
      onAction = {}
    )
  }
}