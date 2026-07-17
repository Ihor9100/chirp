package com.plcoding.feature.auth.presentation.screen.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_email_not_verified
import chirp.feature.auth.presentation.generated.resources.error_invalid_credentials
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.repository.AuthRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.domain.validator.EmailValidator
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.toStringRes
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

class LoginScreenViewModel(
  private val authRepository: AuthRepository,
  private val preferencesRepository: PreferencesRepository,
) : BaseScreenViewModel<LoginUiState>() {

  override fun getUiState(): LoginUiState {
    return LoginUiState()
  }

  override fun onInitialize() {
    super.onInitialize()
    subscribeToState()
  }

  private fun subscribeToState() {
    combine(
      snapshotFlow { screenUiState.value.uiState.emailState.text.toString() },
      snapshotFlow { screenUiState.value.uiState.passwordState.text.toString() },
      screenUiState.map { it.hasLoader() }.distinctUntilChanged(),
    ) { email, password, isLoading ->
      val primaryButtonIsEnable = EmailValidator.validate(email) &&
        password.isNotBlank() &&
        !isLoading

      updateUiState {
        copy(primaryButtonIsEnable = primaryButtonIsEnable)
      }
    }.launchIn(viewModelScope)
  }

  fun onAction(action: LoginScreenAction) {
    when (action) {
      is LoginScreenAction.OnTextFieldSecureToggleClick -> updateUiState {
        copy(passwordIsSecureMode = !passwordIsSecureMode)
      }
      is LoginScreenAction.OnPrimaryButtonClick -> handlePrimaryButtonClick()
      else -> Unit
    }
  }

  private fun handlePrimaryButtonClick() {
    launchLoadable {
      authRepository
        .login(
          email = screenUiState.value.uiState.emailState.text.toString(),
          password = screenUiState.value.uiState.passwordState.text.toString(),
        )
        .onFailure { handleFailure(it) }
        .onSuccess { handleSuccess(it) }
    }
  }

  private fun handleFailure(error: DataError.Remote) {
    val errorRes = when (error) {
      DataError.Remote.UNAUTHORIZED -> Res.string.error_invalid_credentials
      DataError.Remote.FORBIDDEN -> Res.string.error_email_not_verified
      else -> error.toStringRes()
    }
    updateUiState {
      copy(errorRes = errorRes)
    }
  }

  private suspend fun handleSuccess(authInfo: AuthInfo) {
    preferencesRepository.saveAuthInfo(authInfo)
    updateUiState {
      copy(logInSuccessEvent = Event(Unit))
    }
  }
}

