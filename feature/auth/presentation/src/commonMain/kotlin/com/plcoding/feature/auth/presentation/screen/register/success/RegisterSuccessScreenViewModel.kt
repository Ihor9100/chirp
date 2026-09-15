package com.plcoding.feature.auth.presentation.screen.register.success

import androidx.lifecycle.SavedStateHandle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.resent_verification_email
import chirp.feature.auth.presentation.generated.resources.verification_email_sent_to_x
import com.plcoding.core.domain.repository.AuthRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.toStringRes

class RegisterSuccessScreenViewModel(
  private val authRepository: AuthRepository,
  savedStateHandle: SavedStateHandle,
) : BaseScreenViewModel<RegisterSuccessUiState>() {

  private val email = savedStateHandle.get<String>("email")

  override fun getUiState(): RegisterSuccessUiState {
    return RegisterSuccessUiState()
  }

  override fun onInitialize() {
    super.onInitialize()
    updateUiState {
      copy(
        description = TextProvider.Resource(
          Res.string.verification_email_sent_to_x,
          listOf(email!!),
        ),
      )
    }
  }

  fun onAction(action: RegisterSuccessScreenAction) {
    when (action) {
      is RegisterSuccessScreenAction.OnResendClick -> resendVerificationEmail()
      else -> Unit
    }
  }

  private fun resendVerificationEmail() {
    launchLoadable {
      authRepository
        .resendVerificationEmail(email!!)
        .onFailure { handleFailure(it) }
        .onSuccess { showSnackbar(Res.string.resent_verification_email) }
    }
  }

  private fun handleFailure(error: DataError.Remote) {
    updateUiState {
      copy(secondaryButtonErrorRes = error.toStringRes())
    }
  }
}