package com.plcoding.feature.auth.presentation.register

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
import com.plcoding.core.designsystem.components.brand.ChirpBrandLogo
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
fun RegisterScreen(
  viewModel: RegisterViewModel = koinViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  RegisterContent(
    state = state,
    onAction = viewModel::onAction
  )
}

@Composable
fun RegisterContent(
  state: RegisterState,
  onAction: (RegisterAction) -> Unit,
) {
  ChirpTheme {
    ChirAdaptiveFormLayout(
      modifier = Modifier.fillMaxSize(),
      logo = { ChirpBrandLogo() },
      title = stringResource(state.titleRes),
      error = state.errorRes?.let { stringResource(it) },
    ) {
      ChirpTextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(state.usernameTopTitleRes),
        textFieldState = state.usernameState,
        inputPlaceholder = stringResource(state.usernamePlaceholderRes),
        bottomTitle = state.usernameBottomTitleRes?.let { stringResource(it) },
        keyboardType = KeyboardType.Text,
        isError = state.usernameIsError,
        onFocusChanged = {
          onAction(
            RegisterAction.OnTextFieldFocusGain(
              isFocused = it,
              inputField = RegisterViewModel.InputField.USERNAME,
            )
          )
        }
      )
      Spacer(Modifier.height(20.dp))
      ChirpTextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(state.emailTopTitleRes),
        textFieldState = state.emailState,
        inputPlaceholder = stringResource(state.emailPlaceholderRes),
        bottomTitle = state.emailBottomTitleRes?.let { stringResource(it) },
        keyboardType = KeyboardType.Text,
        isError = state.emailIsError,
        onFocusChanged = {
          onAction(
            RegisterAction.OnTextFieldFocusGain(
              isFocused = it,
              inputField = RegisterViewModel.InputField.EMAIL,
            )
          )
        }
      )
      Spacer(Modifier.height(20.dp))
      ChirpTextFieldPassword(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(state.passwordTopTitleRes),
        textFieldState = state.passwordState,
        inputPlaceholder = stringResource(state.passwordPlaceholderRes),
        bottomTitle = state.passwordBottomTitleRes?.let { stringResource(it) },
        isError = state.passwordIsError,
        isSecureMode = state.passwordIsSecureMode,
        onFocusChanged = {
          onAction(
            RegisterAction.OnTextFieldFocusGain(
              isFocused = it,
              inputField = RegisterViewModel.InputField.PASSWORD,
            )
          )
        },
        onSecureToggleClick = { onAction(RegisterAction.OnTextFieldSecureToggleClick) }
      )
      Spacer(Modifier.height(32.dp))
      ChirpButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(state.primaryButtonTitleRes),
        style = ChirpButtonStyle.PRIMARY,
        isLoading = state.primaryButtonIsLoading,
        onClick = { onAction(RegisterAction.OnPrimaryButtonClick) }
      )
      ChirpButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(state.secondaryButtonTitleRes),
        style = ChirpButtonStyle.SECONDARY,
        onClick = { onAction(RegisterAction.OnSecondaryButtonClick) }
      )
    }
  }
}

@Preview
@Composable
private fun RegisterPreview() {
  ChirpTheme {
    RegisterContent(
      state = RegisterState(),
      onAction = {}
    )
  }
}