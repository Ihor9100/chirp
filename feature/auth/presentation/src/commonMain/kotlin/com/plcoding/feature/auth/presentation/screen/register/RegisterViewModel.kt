package com.plcoding.feature.auth.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_account_exists
import chirp.feature.auth.presentation.generated.resources.error_invalid_email
import chirp.feature.auth.presentation.generated.resources.error_invalid_password
import chirp.feature.auth.presentation.generated.resources.error_invalid_username
import com.plcoding.core.domain.network.service.AuthService
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import com.plcoding.core.domain.error.DataError
import com.plcoding.core.domain.validator.EmailValidator
import com.plcoding.core.domain.validator.PasswordValidator
import com.plcoding.core.domain.validator.UsernameValidator
import com.plcoding.core.presentation.utils.getString
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
  private val authService: AuthService,
) : ViewModel() {

  private var hasLoadedInitialData = false

  private val _event = Channel<RegisterEvent>()
  val event = _event.receiveAsFlow()

  private val _state = MutableStateFlow(RegisterState())
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
      initialValue = RegisterState()
    )

  fun onAction(action: RegisterAction) {
    when (action) {
      is RegisterAction.OnTextFieldFocusGain -> clearInputFieldError(action)
      is RegisterAction.OnTextFieldSecureToggleClick -> handleTextFieldSecureToggleClick()
      is RegisterAction.OnPrimaryButtonClick -> handlePrimaryButtonClick()
      else -> Unit
    }
  }

  private fun clearInputFieldError(action: RegisterAction.OnTextFieldFocusGain) {
    if (action.isFocused) {
      _state.update {
        when (action.inputField) {
          InputField.USERNAME -> it.copy(
            usernameIsError = false,
            usernameBottomTitleRes = null,
          )
          InputField.EMAIL -> it.copy(
            emailIsError = false,
            emailBottomTitleRes = null,
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
      val email = state.value.emailState.text.toString()
      _state.update {
        it.copy(primaryButtonIsLoading = true)
      }

      authService
        .register(
          username = state.value.usernameState.text.toString(),
          email = email,
          password = state.value.passwordState.text.toString(),
        )
        .onFailure { error ->
          val errorRes = when (error) {
            DataError.Remote.CONFLICT -> Res.string.error_account_exists
            else -> error.getString()
          }
          _state.update {
            it.copy(errorRes = errorRes)
          }
        }
        .onSuccess {
          _event.send(RegisterEvent.Success(email))
        }

      _state.update {
        it.copy(primaryButtonIsLoading = false)
      }
    }
  }

  private fun areFieldsValid(): Boolean {
    val isUsernameValid = UsernameValidator.validate(state.value.usernameState.text.toString())
    val isEmailValid = EmailValidator.validate(state.value.emailState.text.toString())
    val isPasswordValid = PasswordValidator.validate(state.value.passwordState.text.toString())

    val usernameError = if (!isUsernameValid) Res.string.error_invalid_username else null
    val emailError = if (!isEmailValid) Res.string.error_invalid_email else null
    val passwordError = if (!isPasswordValid) Res.string.error_invalid_password else null

    _state.update {
      it.copy(
        usernameIsError = usernameError != null,
        usernameBottomTitleRes = usernameError,
        emailIsError = emailError != null,
        emailBottomTitleRes = emailError,
        passwordIsError = passwordError != null,
        passwordBottomTitleRes = passwordError,
      )
    }

    return isUsernameValid && isEmailValid && isPasswordValid
  }

  enum class InputField {
    USERNAME,
    EMAIL,
    PASSWORD;
  }
}