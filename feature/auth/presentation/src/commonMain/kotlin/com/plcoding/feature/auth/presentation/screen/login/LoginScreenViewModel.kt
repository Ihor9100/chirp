package com.plcoding.feature.auth.presentation.screen.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_email_not_verified
import chirp.feature.auth.presentation.generated.resources.error_invalid_credentials
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.local.PreferencesLocalRepository
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.domain.validator.EmailValidator
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.screen.base.Overlay
import com.plcoding.core.presentation.utils.getStringRes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

class LoginScreenViewModel(
  private val authRemoteRepository: AuthRemoteRepository,
  private val preferencesLocalRepository: PreferencesLocalRepository,
) : BaseScreenViewModel<LoginScreenContent>() {

  override fun getInitialContent(): LoginScreenContent {
    return LoginScreenContent()
  }

  override fun onInitialized() {
    super.onInitialized()
    subscribeToState()
  }

  private fun subscribeToState() {
    combine(
      snapshotFlow { state.value.content.emailState.text.toString() },
      snapshotFlow { state.value.content.passwordState.text.toString() },
      state.map { it.baseContent.overlay == Overlay.BLOCKABLE }.distinctUntilChanged(),
    ) { email, password, showLoader ->
      val primaryButtonIsEnable = EmailValidator.validate(email) &&
        password.isNotBlank() &&
        !showLoader

      //      mutableState.update {
      //        it.copy(primaryButtonIsEnable = primaryButtonIsEnable)
      //      }
    }.launchIn(viewModelScope)
  }

  fun onAction(action: LoginScreenAction) {
    when (action) {
      is LoginScreenAction.OnTextFieldSecureToggleClick -> mutableState.updateContent {
        copy(passwordIsSecureMode = !passwordIsSecureMode)
      }
      is LoginScreenAction.OnPrimaryButtonClick -> handlePrimaryButtonClick()
      else -> Unit
    }
  }

  private fun handlePrimaryButtonClick() {
    launchBlockable {
      delay(3000)
      authRemoteRepository
        .login(
          email = state.value.content.emailState.text.toString(),
          password = state.value.content.passwordState.text.toString(),
        )
        .onFailure { handleFailure(it) }
        .onSuccess { handleSuccess(it) }
    }
  }

  private fun handleFailure(error: DataError.Remote) {
    val errorRes = when (error) {
      DataError.Remote.UNAUTHORIZED -> Res.string.error_invalid_credentials
      DataError.Remote.FORBIDDEN -> Res.string.error_email_not_verified
      else -> error.getStringRes()
    }
    mutableState.updateContent {
      copy(errorRes = errorRes)
    }
  }

  private suspend fun handleSuccess(authInfo: AuthInfo) {
    preferencesLocalRepository.saveAuthInfo(authInfo)
    mutableState.updateContent {
      copy(logInSuccessEvent = Event(Unit))
    }
  }
}

