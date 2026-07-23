package com.plcoding.feature.auth.presentation.screen.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.components.AppLogo
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveFormLayout
import com.plcoding.core.designsystem.components.textfields.TextFieldPassword
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.model.ScreenUiState
import com.plcoding.core.presentation.screen.base.BaseScreen
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
  val state by viewModel.screenUiState.collectAsStateWithLifecycle()

  viewModel.event.CollectEvent { event ->
    when (event) {
      is RegisterScreenEvent.Success -> openRegisterSuccess(event.email)
    }
  }

  BaseScreen(
    baseUiState = state.baseUiState,
    backgroundColor = MaterialTheme.colorScheme.background,
    isSafeDrawing = false,
  ) {
    Content(
      uiState = state.uiState,
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
private fun Content(
  uiState: RegisterUiState,
  onAction: (RegisterScreenAction) -> Unit,
) {
    AdaptiveFormLayout(
      modifier = Modifier.fillMaxSize(),
      logo = { AppLogo() },
      title = stringResource(uiState.titleRes),
      error = uiState.errorRes?.let { stringResource(it) },
    ) {
      TextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(uiState.usernameTopTitleRes),
        textFieldState = uiState.usernameState,
        inputPlaceholder = stringResource(uiState.usernamePlaceholderRes),
        bottomTitle = uiState.usernameBottomTitleRes?.let { stringResource(it) },
        keyboardType = KeyboardType.Text,
        isError = uiState.usernameIsError,
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
      TextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(uiState.emailTopTitleRes),
        textFieldState = uiState.emailState,
        inputPlaceholder = stringResource(uiState.emailPlaceholderRes),
        bottomTitle = uiState.emailBottomTitleRes?.let { stringResource(it) },
        keyboardType = KeyboardType.Text,
        isError = uiState.emailIsError,
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
      TextFieldPassword(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(uiState.passwordTopTitleRes),
        textFieldState = uiState.passwordState,
        inputPlaceholder = stringResource(uiState.passwordPlaceholderRes),
        bottomTitle = uiState.passwordBottomTitleRes?.let { stringResource(it) },
        isError = uiState.passwordIsError,
        isSecureMode = uiState.passwordIsSecureMode,
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
      Button(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(uiState.primaryButtonTitleRes),
        style = ButtonStyle.PRIMARY,
        onClick = { onAction(RegisterScreenAction.OnPrimaryButtonClick) }
      )
      Button(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(uiState.secondaryButtonTitleRes),
        style = ButtonStyle.SECONDARY,
        onClick = { onAction(RegisterScreenAction.OnSecondaryButtonClick) }
      )
    }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenUiState = ScreenUiState(RegisterUiState())

  Theme(isDarkTheme) {
    BaseScreen(
      baseUiState = screenUiState.baseUiState,
      backgroundColor = MaterialTheme.colorScheme.background,
    ) {
      Content(
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