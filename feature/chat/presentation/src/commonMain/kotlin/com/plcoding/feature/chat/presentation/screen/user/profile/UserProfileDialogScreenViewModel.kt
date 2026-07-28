@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package com.plcoding.feature.chat.presentation.screen.user.profile

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.domain.validator.PasswordValidator
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.toStringRes
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.domain.repository.LiveChatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class UserProfileDialogScreenViewModel(
  private val chatRepository: ChatRepository,
) : BaseScreenViewModel<UserProfileDialogScreenUiState>() {

  override fun getUiState(): UserProfileDialogScreenUiState {
    return UserProfileDialogScreenUiState()
  }

  override fun onInitialize() {
    super.onInitialize()

    observePasswords()
  }

  private fun observePasswords() {
    val currentPassword = snapshotFlow {
      screenUiState.value.uiState.currentPasswordTextFieldState.text.toString()
    }.map { it.isNotBlank() }

    val newPassword = snapshotFlow {
      screenUiState.value.uiState.newPasswordTextFieldState.text.toString()
    }.map { PasswordValidator.validate(it) }

    combine(
      currentPassword,
      newPassword,
    ) { isCurrentValid, isNewValid ->
      updateUiState {
        copy(
          isPositiveButtonEnable = isCurrentValid && isNewValid,
        )
      }
    }.launchIn(viewModelScope)
  }

  fun handleAction(action: UserProfileDialogScreenAction) {
    when (action) {
      UserProfileDialogScreenAction.OnCloseClick -> TODO()
      UserProfileDialogScreenAction.OnCurrentPasswordEyeClick -> updateUiState {
        copy(isCurrentPasswordSecureMode = !isCurrentPasswordSecureMode)
      }
      UserProfileDialogScreenAction.OnDeleteClick -> TODO()
      UserProfileDialogScreenAction.OnNewPasswordEyeClick -> updateUiState {
        copy(isNewPasswordSecureMode = !isNewPasswordSecureMode)
      }
      UserProfileDialogScreenAction.OnPrimaryButtonClick ->
        UserProfileDialogScreenAction.OnSecondaryButtonClick
      -> TODO()
      UserProfileDialogScreenAction.OnUploadImageClick -> TODO()
    }
  }

  // TODO:  
  private fun changePassword() {
    viewModelScope.launch {
      chatRepository.changePassword(
        currentPassword = screenUiState.value.uiState.currentPasswordTextFieldState.text.toString(),
        newPassword = screenUiState.value.uiState.newPasswordTextFieldState.text.toString(),
      ).onSuccess {
        updateUiState {
          screenUiState.value.uiState.currentPasswordTextFieldState.clearText()
          screenUiState.value.uiState.newPasswordTextFieldState.clearText()
          copy(
          
          )
        }
      }.onFailure {
        val messageRes = when (it) {
          // TODO:
          else -> it.toStringRes()
        }
        showSnackbar(messageRes)
      }
    }
  }
}
