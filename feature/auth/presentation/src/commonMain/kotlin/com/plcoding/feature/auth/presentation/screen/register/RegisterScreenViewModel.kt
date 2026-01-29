package com.plcoding.feature.auth.presentation.screen.register

import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_account_exists
import chirp.feature.auth.presentation.generated.resources.error_invalid_email
import chirp.feature.auth.presentation.generated.resources.error_invalid_password
import chirp.feature.auth.presentation.generated.resources.error_invalid_username
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.domain.validator.EmailValidator
import com.plcoding.core.domain.validator.PasswordValidator
import com.plcoding.core.domain.validator.UsernameValidator
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.getStringRes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class RegisterScreenViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
) : BaseScreenViewModel<RegisterScreenContent>() {

  private val _event = Channel<RegisterScreenEvent>()
  val event = _event.receiveAsFlow()

  override fun getContentPm(): RegisterScreenContent {
    return RegisterScreenContent()
  }

  fun onAction(action: RegisterScreenAction) {
    when (action) {
      is RegisterScreenAction.OnTextFieldFocusGain -> clearInputFieldError(action)
      is RegisterScreenAction.OnTextFieldSecureToggleClick -> handleTextFieldSecureToggleClick()
      is RegisterScreenAction.OnPrimaryButtonClick -> handlePrimaryButtonClick()
      else -> Unit
    }
  }

  private fun clearInputFieldError(action: RegisterScreenAction.OnTextFieldFocusGain) {
    if (action.isFocused) {
      updateContent {
        when (action.inputField) {
          InputField.USERNAME -> copy(
            usernameIsError = false,
            usernameBottomTitleRes = null,
          )
          InputField.EMAIL -> copy(
            emailIsError = false,
            emailBottomTitleRes = null,
          )
          InputField.PASSWORD -> copy(
            passwordIsError = false,
            passwordBottomTitleRes = null,
          )
        }
      }
    }
  }

  private fun handleTextFieldSecureToggleClick() {
    updateContent {
      copy(passwordIsSecureMode = !passwordIsSecureMode)
    }
  }

  private fun handlePrimaryButtonClick() {
    if (!areFieldsValid()) return

    launchLoadable {
      val email = screenState.value.contentPm.emailState.text.toString()

      authRemoteRepository
        .register(
          username = screenState.value.contentPm.usernameState.text.toString(),
          email = email,
          password = screenState.value.contentPm.passwordState.text.toString(),
        )
        .onFailure(::handleFailure)
        .onSuccess { _event.send(RegisterScreenEvent.Success(email)) }
    }
  }

  private fun handleFailure(error: DataError.Remote) {
    val errorRes = when (error) {
      DataError.Remote.CONFLICT -> Res.string.error_account_exists
      else -> error.getStringRes()
    }
    updateContent { copy(errorRes = errorRes) }
  }

  private fun areFieldsValid(): Boolean {
    val isUsernameValid = UsernameValidator.validate(
      screenState.value.contentPm.usernameState.text.toString()
    )
    val isEmailValid = EmailValidator.validate(
      screenState.value.contentPm.emailState.text.toString()
    )
    val isPasswordValid = PasswordValidator.validate(
      screenState.value.contentPm.passwordState.text.toString()
    )

    val usernameError = if (!isUsernameValid) Res.string.error_invalid_username else null
    val emailError = if (!isEmailValid) Res.string.error_invalid_email else null
    val passwordError = if (!isPasswordValid) Res.string.error_invalid_password else null

    updateContent {
      copy(
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