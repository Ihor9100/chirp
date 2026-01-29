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
import com.plcoding.core.designsystem.components.AppLogoPc
import com.plcoding.core.designsystem.components.button.ButtonPc
import com.plcoding.core.designsystem.components.button.ButtonPcStyle
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveFormLayout
import com.plcoding.core.designsystem.components.textfields.TextFieldPassword
import com.plcoding.core.designsystem.components.textfields.TextFieldPlain
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreen
import com.plcoding.core.presentation.screen.model.ScreenStatePm
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
  val state by viewModel.screenState.collectAsStateWithLifecycle()

  viewModel.event.CollectEvent { event ->
    when (event) {
      is RegisterScreenEvent.Success -> openRegisterSuccess(event.email)
    }
  }

  BaseScreen(
    baseContentPm = state.baseContentPm
  ) {
    Content(
      contentPm = state.contentPm,
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
  contentPm: RegisterScreenContentPm,
  onAction: (RegisterScreenAction) -> Unit,
) {
    AdaptiveFormLayout(
      modifier = Modifier.fillMaxSize(),
      logo = { AppLogoPc() },
      title = stringResource(contentPm.titleRes),
      error = contentPm.errorRes?.let { stringResource(it) },
    ) {
      TextFieldPlain(
        modifier = Modifier.fillMaxWidth(),
        topTitle = stringResource(contentPm.usernameTopTitleRes),
        textFieldState = contentPm.usernameState,
        inputPlaceholder = stringResource(contentPm.usernamePlaceholderRes),
        bottomTitle = contentPm.usernameBottomTitleRes?.let { stringResource(it) },
        keyboardType = KeyboardType.Text,
        isError = contentPm.usernameIsError,
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
        topTitle = stringResource(contentPm.emailTopTitleRes),
        textFieldState = contentPm.emailState,
        inputPlaceholder = stringResource(contentPm.emailPlaceholderRes),
        bottomTitle = contentPm.emailBottomTitleRes?.let { stringResource(it) },
        keyboardType = KeyboardType.Text,
        isError = contentPm.emailIsError,
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
        topTitle = stringResource(contentPm.passwordTopTitleRes),
        textFieldState = contentPm.passwordState,
        inputPlaceholder = stringResource(contentPm.passwordPlaceholderRes),
        bottomTitle = contentPm.passwordBottomTitleRes?.let { stringResource(it) },
        isError = contentPm.passwordIsError,
        isSecureMode = contentPm.passwordIsSecureMode,
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
      ButtonPc(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(contentPm.primaryButtonTitleRes),
        style = ButtonPcStyle.PRIMARY,
        onClick = { onAction(RegisterScreenAction.OnPrimaryButtonClick) }
      )
      ButtonPc(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(contentPm.secondaryButtonTitleRes),
        style = ButtonPcStyle.SECONDARY,
        onClick = { onAction(RegisterScreenAction.OnSecondaryButtonClick) }
      )
    }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenStatePm = ScreenStatePm(RegisterScreenContentPm())

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