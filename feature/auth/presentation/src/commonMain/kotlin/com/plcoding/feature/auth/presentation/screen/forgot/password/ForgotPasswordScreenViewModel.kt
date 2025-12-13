package com.plcoding.feature.auth.presentation.screen.forgot.password

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.validator.EmailValidator
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class ForgotPasswordScreenViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
) : BaseScreenViewModel<ForgotPasswordState>() {

  override fun getInitialState(): ForgotPasswordState {
    return ForgotPasswordState()
  }

  override fun onInitialized() {
    super.onInitialized()
    subscribeToState()
  }

  private fun subscribeToState() {
    combine(
      snapshotFlow { state.value.emailState.text.toString() },
      state.map { it.showLoader }.distinctUntilChanged(),
    ) { email, showLoader ->
      mutableState.update {
        it.copy(
          primaryButtonIsEnable = EmailValidator.validate(email) && !showLoader
        )
      }
    }.launchIn(viewModelScope)
  }

  fun onAction(action: ForgotPasswordAction) {
    when (action) {
      else -> TODO("Handle actions")
    }
  }

  private fun handleSubmitClick() {
    if (state.value.showLoader) return

    mutableState.update { it.copy(showLoader = true) }
    // TODO:
    mutableState.update { it.copy(showLoader = true) }
  }
}