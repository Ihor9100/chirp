package com.plcoding.feature.auth.presentation.screen.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.core.presentation.generated.resources.chirp
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.log_in
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.register
import chirp.feature.auth.presentation.generated.resources.username
import chirp.feature.auth.presentation.generated.resources.welcome_to_chirp
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
import chirp.core.presentation.generated.resources.Res as CoreRes

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
    RegisterScreenContent(
      uiState = state.uiState,
      onAction = {
        when (it) {
          RegisterScreenAction.OnLoginClick -> openLogin()
          else -> viewModel.onAction(it)
        }
      }
    )
  }
}

@Composable
fun RegisterScreenContent(
  uiState: RegisterScreenUiState,
  onAction: (RegisterScreenAction) -> Unit,
) {
  AdaptiveFormLayout(
    modifier = Modifier.fillMaxSize(),
    logo = { AppLogo() },
    title = stringResource(Res.string.welcome_to_chirp),
    error = uiState.errorRes?.let { stringResource(it) },
    errorTestTag = RegisterScreenTestTag.ERROR.value,
  ) {
    TextFieldPlain(
      modifier = Modifier.fillMaxWidth(),
      topTitle = stringResource(Res.string.username),
      textFieldState = uiState.usernameState,
      inputPlaceholder = stringResource(CoreRes.string.chirp),
      testTag = RegisterScreenTestTag.USERNAME_TEXT_FIELD.value,
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
      topTitle = stringResource(Res.string.email),
      textFieldState = uiState.emailState,
      inputPlaceholder = stringResource(Res.string.email),
      testTag = RegisterScreenTestTag.EMAIL_TEXT_FIELD.value,
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
      topTitle = stringResource(Res.string.password),
      textFieldState = uiState.passwordState,
      inputPlaceholder = stringResource(Res.string.password),
      bottomTitle = uiState.passwordBottomTitleRes?.let { stringResource(it) },
      testTag = RegisterScreenTestTag.PASSWORD_TEXT_FIELD.value,
      secureIconTestTag = RegisterScreenTestTag.PASSWORD_SECURE_ICON.value,
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
      onSecureToggleClick = { onAction(RegisterScreenAction.OnPasswordSecureIconClick) }
    )
    Spacer(Modifier.height(32.dp))
    Button(
      modifier = Modifier
        .fillMaxWidth()
        .testTag(RegisterScreenTestTag.REGISTER_BUTTON.value),
      text = stringResource(Res.string.register),
      style = ButtonStyle.PRIMARY,
      onClick = { onAction(RegisterScreenAction.OnRegisterClick) }
    )
    Button(
      modifier = Modifier
        .fillMaxWidth()
        .testTag(RegisterScreenTestTag.LOGIN_BUTTON.value),
      text = stringResource(Res.string.log_in),
      style = ButtonStyle.SECONDARY,
      onClick = { onAction(RegisterScreenAction.OnLoginClick) }
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val screenUiState = ScreenUiState(RegisterScreenUiState())

  Theme(isDarkTheme) {
    BaseScreen(
      baseUiState = screenUiState.baseUiState,
      backgroundColor = MaterialTheme.colorScheme.background,
    ) {
      RegisterScreenContent(
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

enum class RegisterScreenTestTag(val value: String) {
  USERNAME_TEXT_FIELD("register_screen_username_text_field"),
  EMAIL_TEXT_FIELD("register_screen_email_text_field"),
  PASSWORD_TEXT_FIELD("register_screen_password_text_field"),
  PASSWORD_SECURE_ICON("register_screen_password_text_field_secure_icon"),
  REGISTER_BUTTON("register_screen_register_button"),
  LOGIN_BUTTON("register_screen_login_button"),
  ERROR("register_screen_error"),
}
