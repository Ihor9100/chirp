package com.plcoding.feature.auth.presentation.screen.reset.password

import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_reset_password_token_invalid
import chirp.feature.auth.presentation.generated.resources.forgot_password_email_sent_successfully
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
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

class ResetPasswordScreenViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
  savedStateHandle: SavedStateHandle,
) : BaseScreenViewModel<ResetPasswordScreenContent>() {

  private val token: String = savedStateHandle["token"] ?: error("Token should be passed")

  override fun getInitialContent(): ResetPasswordScreenContent {
    return ResetPasswordScreenContent()
  }

  override fun onInitialized() {
    super.onInitialized()
    subscribeToState()
  }

  private fun subscribeToState() {
    combine(
      snapshotFlow { state.value.content.passwordState.text.toString() },
      state.map { it.isLoading() }.distinctUntilChanged(),
    ) { email, isLoading ->
      updateContent {
        copy(passwordIsEnable = EmailValidator.validate(email) && !isLoading)
      }
    }.launchIn(viewModelScope)
  }

  fun onAction(action: ResetPasswordScreenAction) {
    when (action) {
      is ResetPasswordScreenAction.OnTextFieldSecureToggleClick -> handleTextFieldSecureToggleClick()
      is ResetPasswordScreenAction.OnPrimaryButtonClick -> handlePrimaryButtonClick()
      is ResetPasswordScreenAction.OnSnackbarDisappeared -> handleSnackbarDisappeared()
    }
  }

  private fun handleTextFieldSecureToggleClick() {
    updateContent {
      copy(passwordIsSecureMode = !passwordIsSecureMode)
    }
  }

  private fun handlePrimaryButtonClick() {
    launchLoadable {
      authRemoteRepository
        .resetPassword(state.value.content.passwordState.text.toString(), token)
        .onFailure(::handleFailure)
        .onSuccess { handleSuccess() }
    }
  }

  private fun handleFailure(error: DataError.Remote) {
    val errorRes = when (error) {
      DataError.Remote.UNAUTHORIZED -> Res.string.error_reset_password_token_invalid
      DataError.Remote.CONFLICT -> Res.string.error_same_password
      else -> error.getStringRes()
    }
    updateContent {
      copy(errorRes = errorRes)
    }
  }

  private fun handleSuccess() {
    updateContent {
      copy(resetSuccessEvent = Event(Res.string.forgot_password_email_sent_successfully))
    }
  }

  private fun handleSnackbarDisappeared() {
    updateContent {
      copy(navigateToLoginEvent = Event(Unit))
    }
  }
}
