package com.plcoding.feature.auth.presentation.screen.register.success

import androidx.lifecycle.SavedStateHandle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.resent_verification_email
import chirp.feature.auth.presentation.generated.resources.verification_email_sent_to_x
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.core.presentation.utils.getStringRes

class RegisterSuccessScreenViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
  savedStateHandle: SavedStateHandle,
) : BaseScreenViewModel<RegisterSuccessScreenContentPm>() {

  private val email = savedStateHandle.get<String>("email") ?: throw IllegalArgumentException()

  override fun getContentPm(): RegisterSuccessScreenContentPm {
    return RegisterSuccessScreenContentPm(
      description = TextProvider.Resource(
        id = Res.string.verification_email_sent_to_x,
        args = listOf(email),
      )
    )
  }

  fun onAction(action: RegisterSuccessScreenAction) {
    when (action) {
      is RegisterSuccessScreenAction.SecondaryButtonClick -> resendVerificationEmail()
      else -> Unit
    }
  }

  private fun resendVerificationEmail() {
    launchLoadable {
      authRemoteRepository
        .resendVerificationEmail(email)
        .onFailure { handleFailure(it) }
        .onSuccess { showSnackbar(Res.string.resent_verification_email) }
    }
  }

  private fun handleFailure(error: DataError.Remote) {
    updateContentPm {
      copy(secondaryButtonErrorRes = error.getStringRes())
    }
  }
}