package com.plcoding.feature.auth.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_invalid_credentials
import chirp.feature.auth.presentation.generated.resources.error_invalid_password
import com.plcoding.core.domain.validation.EmailValidator
import com.plcoding.core.domain.validation.PasswordValidator
import com.plcoding.core.domain.validation.UsernameValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

  private var hasLoadedInitialData = false

  private val _state = MutableStateFlow(LoginState())
  val state = _state
    .onStart {
      if (!hasLoadedInitialData) {
        /** Load initial data here **/
        hasLoadedInitialData = true
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000L),
      initialValue = LoginState()
    )

  fun onAction(action: LoginAction) {
    when (action) {
      is LoginAction.OnTextFieldFocusGain -> clearInputFieldError(action)
      is LoginAction.OnTextFieldSecureToggleClick -> handleTextFieldSecureToggleClick()
      is LoginAction.OnPrimaryButtonClick -> handlePrimaryButtonClick()
      else -> Unit
    }
  }

  private fun clearInputFieldError(action: LoginAction.OnTextFieldFocusGain) {
    if (action.isFocused) {
      _state.update {
        when (action.inputField) {
          InputField.USERNAME -> it.copy(
            usernameOrEmailIsError = false,
            usernameOrEmailBottomTitleRes = null,
          )
          InputField.PASSWORD -> it.copy(
            passwordIsError = false,
            passwordBottomTitleRes = null,
          )
        }
      }
    }
  }

  private fun handleTextFieldSecureToggleClick() {
    _state.update {
      it.copy(passwordIsSecureMode = !it.passwordIsSecureMode)
    }
  }

  private fun handlePrimaryButtonClick() {
    if (!areFieldsValid()) return

    viewModelScope.launch {
      _state.update {
        it.copy(primaryButtonIsLoading = true)
      }

      // TODO:

      _state.update {
        it.copy(primaryButtonIsLoading = false)
      }
    }
  }

  private fun areFieldsValid(): Boolean {
    val usernameOrEmail = state.value.usernameOrEmailState.text.toString()
    val isUsernameOrEmailValid = UsernameValidator.validate(usernameOrEmail) ||
      EmailValidator.validate(usernameOrEmail)
    val isPasswordValid = PasswordValidator.validate(state.value.passwordState.text.toString())

    val usernameOrEmailError =
      if (!isUsernameOrEmailValid) Res.string.error_invalid_credentials else null
    val passwordError = if (!isPasswordValid) Res.string.error_invalid_password else null

    _state.update {
      it.copy(
        usernameOrEmailIsError = usernameOrEmailError != null,
        usernameOrEmailBottomTitleRes = usernameOrEmailError,
        passwordIsError = passwordError != null,
        passwordBottomTitleRes = passwordError,
      )
    }

    return isUsernameOrEmailValid && isPasswordValid
  }

  enum class InputField {
    USERNAME,
    PASSWORD;
  }
}