package com.plcoding.feature.auth.presentation.screen.register.success

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.resent_verification_email
import com.plcoding.core.domain.error.DataError
import com.plcoding.core.domain.network.service.AuthService
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.utils.getStringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSuccessViewModel(
  private val authService: AuthService,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private var hasLoadedInitialData = false

  private val _state = MutableStateFlow(RegisterSuccessState())
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
      initialValue = RegisterSuccessState()
    )

  private val email = savedStateHandle.get<String>("email") ?: throw IllegalArgumentException()

  fun onAction(action: RegisterSuccessAction) {
    when (action) {
      is RegisterSuccessAction.SecondaryButtonClick -> resendVerificationEmail()
      else -> Unit
    }
  }

  private fun resendVerificationEmail() {
    if (state.value.hasOngoingRequest) return

    viewModelScope.launch {
      _state.update { it.copy(hasOngoingRequest = true) }

      authService
        .resendVerificationEmail(email)
        .onFailure { handleFailure(it) }
        .onSuccess { handleSuccess() }

      _state.update { it.copy(hasOngoingRequest = false) }
    }
  }

  private fun handleFailure(error: DataError.Remote) {
    _state.update {
      it.copy(
        secondaryButtonErrorRes = error.getStringRes(),
        hasOngoingRequest = false,
      )
    }
  }

  private fun handleSuccess() {
    _state.update {
      it.copy(
        hasOngoingRequest = false,
        snackbarEvent = Event(Res.string.resent_verification_email)
      )
    }
  }
}