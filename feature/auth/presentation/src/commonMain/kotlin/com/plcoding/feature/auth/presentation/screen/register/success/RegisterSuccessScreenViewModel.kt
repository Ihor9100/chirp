package com.plcoding.feature.auth.presentation.screen.register.success

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.resent_verification_email
import chirp.feature.auth.presentation.generated.resources.verification_email_sent_to_x
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.core.presentation.utils.getStringRes
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSuccessScreenViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
  savedStateHandle: SavedStateHandle,
) : BaseScreenViewModel<RegisterSuccessState>() {

  private val email = savedStateHandle.get<String>("email") ?: throw IllegalArgumentException()

  override fun getInitialState(): RegisterSuccessState {
    return RegisterSuccessState(
      description = TextProvider.Resource(
        id = Res.string.verification_email_sent_to_x,
        args = listOf(email),
      )
    )
  }

  fun onAction(action: RegisterSuccessAction) {
    when (action) {
      is RegisterSuccessAction.SecondaryButtonClick -> resendVerificationEmail()
      else -> Unit
    }
  }

  private fun resendVerificationEmail() {
    if (state.value.hasOngoingRequest) return

    viewModelScope.launch {
      mutableState.update { it.copy(hasOngoingRequest = true) }

      authRemoteRepository
        .resendVerificationEmail(email)
        .onFailure { handleFailure(it) }
        .onSuccess { handleSuccess() }

      mutableState.update { it.copy(hasOngoingRequest = false) }
    }
  }

  private fun handleFailure(error: DataError.Remote) {
    mutableState.update {
      it.copy(
        secondaryButtonErrorRes = error.getStringRes(),
        hasOngoingRequest = false,
      )
    }
  }

  private fun handleSuccess() {
    mutableState.update {
      it.copy(
        hasOngoingRequest = false,
        snackbarEvent = Event(Res.string.resent_verification_email)
      )
    }
  }
}