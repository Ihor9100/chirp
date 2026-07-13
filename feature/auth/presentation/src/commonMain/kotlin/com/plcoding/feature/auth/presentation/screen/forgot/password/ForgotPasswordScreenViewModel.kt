package com.plcoding.feature.auth.presentation.screen.forgot.password

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.forgot_password_email_sent_successfully
import com.plcoding.core.domain.repository.AuthRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.domain.validator.EmailValidator
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.getStringRes
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

class ForgotPasswordScreenViewModel(
  private val authRepository: AuthRepository,
) : BaseScreenViewModel<ForgotPasswordUiState>() {

  override fun getUiState(): ForgotPasswordUiState {
    return ForgotPasswordUiState()
  }

  override fun onInitialize() {
    super.onInitialize()
    subscribeToState()
  }

  private fun subscribeToState() {
    combine(
      snapshotFlow { screenState.value.uiState.emailState.text.toString() },
      screenState.map { it.hasLoader() }.distinctUntilChanged(),
    ) { email, isLoading ->
      updateUiState {
        copy(primaryButtonIsEnable = EmailValidator.validate(email) && !isLoading)
      }
    }.launchIn(viewModelScope)
  }

  fun onAction(action: ForgotPasswordScreenAction) {
    when (action) {
      is ForgotPasswordScreenAction.OnSubmitClick -> handleSubmitClick()
    }
  }

  private fun handleSubmitClick() {
    launchLoadable {
      authRepository
        .forgotPassword(screenState.value.uiState.emailState.text.toString())
        .onFailure(::handleFailure)
        .onSuccess { handleSuccess() }
    }
  }

  private fun handleFailure(error: DataError.Remote) {
    updateUiState {
      copy(errorRes = error.getStringRes())
    }
  }

  private fun handleSuccess() {
    updateUiState {
      copy(snackbarEvent = Event(Res.string.forgot_password_email_sent_successfully))
    }
  }
}