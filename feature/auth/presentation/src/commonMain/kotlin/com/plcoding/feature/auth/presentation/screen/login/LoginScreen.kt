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
import com.plcoding.core.designsystem.components.AppLogo
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.designsystem.components.layout.AdaptiveFormLayout
import com.plcoding.core.designsystem.components.textfields.TextFieldPassword
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.Theme
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
  AdaptiveFormLayout(
    modifier = Modifier.fillMaxSize(),
    logo = { AppLogo() },
    title = stringResource(content.titleRes),
    error = content.errorRes?.let { stringResource(it) },
  ) {
    TextFieldPlain(
      modifier = Modifier.fillMaxWidth(),
      topTitle = stringResource(content.emailTopTitleRes),
      textFieldState = content.emailState,
      inputPlaceholder = stringResource(content.emailPlaceholderRes),
      bottomTitle = null,
      keyboardType = KeyboardType.Text,
    )
    Spacer(Modifier.height(20.dp))
    TextFieldPassword(
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
    Button(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(content.primaryButtonTitleRes),
      style = ButtonStyle.PRIMARY,
      isEnabled = content.primaryButtonIsEnable,
      onClick = { onAction(LoginScreenAction.OnPrimaryButtonClick) }
    )
    Button(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(content.secondaryButtonTitleRes),
      style = ButtonStyle.SECONDARY,
      onClick = { onAction(LoginScreenAction.OnSecondaryButtonClick) }
    )
  }
}

@Preview
@Composable
private fun LoginScreenPreview() {
  Theme {
    LoginScreenContent(
      content = LoginScreenContent(),
      onAction = {}
    )
  }
}