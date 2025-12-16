package com.plcoding.feature.auth.presentation.screen.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.brand.ChirpLogo
import com.plcoding.core.designsystem.components.button.ChirpButton
import com.plcoding.core.designsystem.components.button.ChirpButtonStyle
import com.plcoding.core.designsystem.components.layout.ChirAdaptiveFormLayout
import com.plcoding.core.designsystem.components.textfields.ChirpTextFieldPassword
import com.plcoding.core.designsystem.components.textfields.ChirpTextFieldPlain
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.core.presentation.screen.base.BaseScreenContent
import com.plcoding.core.presentation.utils.CollectEvent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
  viewModel: RegisterScreenViewModel = koinViewModel(),
  openRegisterSuccess: (String) -> Unit,
  openLogin: () -> Unit,
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  viewModel.event.CollectEvent { event ->
    when (event) {
      is RegisterScreenEvent.Success -> openRegisterSuccess(event.email)
    }
  }

  BaseScreenContent(
    baseContent = state.baseContent
  ) {
    RegisterScreenContent(
      content = state.content,
      onAction = {
        when (it) {
          RegisterScreenAction.OnSecondaryButtonClick -> openLogin()
          else -> viewModel.onAction(it)
        }
      }
    )
  }
}

@Composable
fun RegisterScreenContent(
  content: RegisterScreenContent,
  onAction: (RegisterScreenAction) -> Unit,
) {
  ChirpTheme {
    ChirAdaptiveFormLayout(
      modifier = Modifier.fillMaxSize(),
      logo = { ChirpLogo() },
      title = stringResource(content.titleRes),
      error = content.errorRes?.let { stringResource(it) },
    ) {
      ChirpTextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(content.usernameTopTitleRes),
        textFieldState = content.usernameState,
        inputPlaceholder = stringResource(content.usernamePlaceholderRes),
        bottomTitle = content.usernameBottomTitleRes?.let { stringResource(it) },
        keyboardType = KeyboardType.Text,
        isError = content.usernameIsError,
        onFocusChanged = {
          onAction(
            RegisterScreenAction.OnTextFieldFocusGain(
              isFocused = it,
              inputField = RegisterScreenViewModel.InputField.USERNAME,
            )
          )
        }
      )
      Spacer(Modifier.height(20.dp))
      ChirpTextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(content.emailTopTitleRes),
        textFieldState = content.emailState,
        inputPlaceholder = stringResource(content.emailPlaceholderRes),
        bottomTitle = content.emailBottomTitleRes?.let { stringResource(it) },
        keyboardType = KeyboardType.Text,
        isError = content.emailIsError,
        onFocusChanged = {
          onAction(
            RegisterScreenAction.OnTextFieldFocusGain(
              isFocused = it,
              inputField = RegisterScreenViewModel.InputField.EMAIL,
            )
          )
        }
      )
      Spacer(Modifier.height(20.dp))
      ChirpTextFieldPassword(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(content.passwordTopTitleRes),
        textFieldState = content.passwordState,
        inputPlaceholder = stringResource(content.passwordPlaceholderRes),
        bottomTitle = content.passwordBottomTitleRes?.let { stringResource(it) },
        isError = content.passwordIsError,
        isSecureMode = content.passwordIsSecureMode,
        onFocusChanged = {
          onAction(
            RegisterScreenAction.OnTextFieldFocusGain(
              isFocused = it,
              inputField = RegisterScreenViewModel.InputField.PASSWORD,
            )
          )
        },
        onSecureToggleClick = { onAction(RegisterScreenAction.OnTextFieldSecureToggleClick) }
      )
      Spacer(Modifier.height(32.dp))
      ChirpButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(content.primaryButtonTitleRes),
        style = ChirpButtonStyle.PRIMARY,
        onClick = { onAction(RegisterScreenAction.OnPrimaryButtonClick) }
      )
      ChirpButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(content.secondaryButtonTitleRes),
        style = ChirpButtonStyle.SECONDARY,
        onClick = { onAction(RegisterScreenAction.OnSecondaryButtonClick) }
      )
    }
  }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
  ChirpTheme {
    RegisterScreenContent(
      content = RegisterScreenContent(),
      onAction = {}
    )
  }
}