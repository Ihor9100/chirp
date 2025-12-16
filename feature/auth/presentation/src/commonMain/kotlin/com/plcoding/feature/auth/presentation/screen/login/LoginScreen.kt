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
import com.plcoding.core.presentation.screen.base.BaseScreenContent
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
  val state by viewModel.state.collectAsStateWithLifecycle()
  state.content.logInSuccessEvent?.run(openChat)

  BaseScreenContent(
    baseContent = state.baseContent,
  ) {
    LoginScreenContent(
      content = state.content,
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
fun LoginScreenContent(
  content: LoginScreenContent,
  onAction: (LoginScreenAction) -> Unit,
) {
  ChirAdaptiveFormLayout(
    modifier = Modifier.fillMaxSize(),
    logo = { ChirpLogo() },
    title = stringResource(content.titleRes),
    error = content.errorRes?.let { stringResource(it) },
  ) {
    ChirpTextFieldPlain(
      modifier = Modifier.fillMaxWidth(),
      topTitle = stringResource(content.emailTopTitleRes),
      textFieldState = content.emailState,
      inputPlaceholder = stringResource(content.emailPlaceholderRes),
      bottomTitle = null,
      keyboardType = KeyboardType.Text,
    )
    Spacer(Modifier.height(20.dp))
    ChirpTextFieldPassword(
      modifier = Modifier.fillMaxWidth(),
      topTitle = stringResource(content.passwordTopTitleRes),
      textFieldState = content.passwordState,
      inputPlaceholder = stringResource(content.passwordPlaceholderRes),
      bottomTitle = null,
      isSecureMode = content.passwordIsSecureMode,
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
    ChirpButton(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(content.primaryButtonTitleRes),
      style = ChirpButtonStyle.PRIMARY,
      isLoading = content.showLoader,
      enabled = content.primaryButtonIsEnable,
      onClick = { onAction(LoginScreenAction.OnPrimaryButtonClick) }
    )
    ChirpButton(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(content.secondaryButtonTitleRes),
      style = ChirpButtonStyle.SECONDARY,
      onClick = { onAction(LoginScreenAction.OnSecondaryButtonClick) }
    )
  }
}

@Preview
@Composable
private fun LoginScreenPreview() {
  ChirpTheme {
    LoginScreenContent(
      content = LoginScreenContent(),
      onAction = {}
    )
  }
}