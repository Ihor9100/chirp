package com.plcoding.feature.auth.presentation.screen.forgot.password

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.validator.EmailValidator
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.screen.base.Overlay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

class ForgotPasswordScreenViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
) : BaseScreenViewModel<ForgotPasswordScreenContent>() {

  override fun getInitialContent(): ForgotPasswordScreenContent {
    return ForgotPasswordScreenContent()
  }

  override fun onInitialized() {
    super.onInitialized()
    subscribeToState()
  }

  private fun subscribeToState() {
    combine(
      snapshotFlow { state.value.content.emailState.text.toString() },
      state.map { it.isLoading() }.distinctUntilChanged(),
    ) { email, showLoader ->
      mutableState.updateContent {
        copy(primaryButtonIsEnable = EmailValidator.validate(email) && !showLoader)
      }
    }.launchIn(viewModelScope)
  }

  fun onAction(action: ForgotPasswordScreenAction) {
    when (action) {
      is ForgotPasswordScreenAction.OnSubmitClick -> handleSubmitClick()
    }
  }

  private fun handleSubmitClick() {
    launchWithOverlays(setOf(Overlay.BLOCKABLE, Overlay.LOADABLE)) {
      // TODO:
    }
  }
}