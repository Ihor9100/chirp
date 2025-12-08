package com.plcoding.feature.auth.presentation.screen.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_email_not_verified
import chirp.feature.auth.presentation.generated.resources.error_invalid_credentials
import com.plcoding.core.domain.repository.local.PreferencesLocalRepository
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.domain.validator.EmailValidator
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.utils.getStringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
  private val preferencesLocalRepository: PreferencesLocalRepository,
) : ViewModel() {

  private var hasLoadedInitialData = false

  private val _state = MutableStateFlow(LoginState())
  val state = _state
    .onStart {
      if (!hasLoadedInitialData) {
        subscribeToState()
        /** Load initial data here **/
        hasLoadedInitialData = true
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000L),
      initialValue = LoginState()
    )

  private fun subscribeToState() {
    combine(
      snapshotFlow { state.value.emailState.text.toString() },
      snapshotFlow { state.value.passwordState.text.toString() },
      state.map { it.hasOngoingRequest }.distinctUntilChanged(),
    ) { email, password, hasOngoingRequest ->
      val primaryButtonIsEnable = EmailValidator.validate(email) &&
        password.isNotBlank() &&
        !hasOngoingRequest

      _state.update {
        it.copy(primaryButtonIsEnable = primaryButtonIsEnable)
      }
    }.launchIn(viewModelScope)
  }

  fun onAction(action: LoginAction) {
    when (action) {
      is LoginAction.OnTextFieldSecureToggleClick -> _state.update {
        it.copy(passwordIsSecureMode = !it.passwordIsSecureMode)
      }
      is LoginAction.OnPrimaryButtonClick -> handlePrimaryButtonClick()
      else -> Unit
    }
  }

  private fun handlePrimaryButtonClick() {
    if (state.value.hasOngoingRequest) return

    viewModelScope.launch {
      _state.update {
        it.copy(hasOngoingRequest = true)
      }

      authRemoteRepository
        .login(
          email = state.value.emailState.text.toString(),
          password = state.value.passwordState.text.toString(),
        )
        .onFailure {
          val errorRes = when (it) {
            DataError.Remote.UNAUTHORIZED -> Res.string.error_invalid_credentials
            DataError.Remote.FORBIDDEN -> Res.string.error_email_not_verified
            else -> it.getStringRes()
          }
          _state.update { state ->
            state.copy(
              errorRes = errorRes,
              hasOngoingRequest = false,
            )
          }
        }
        .onSuccess {
          preferencesLocalRepository.saveAuthInfo(it)

          _state.update { state ->
            state.copy(
              hasOngoingRequest = false,
              logInSuccessEvent = Event(Unit)
            )
          }
        }

      _state.update {
        it.copy(hasOngoingRequest = false)
      }
    }
  }
}
