@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package com.plcoding.feature.chat.presentation.screen.user.profile

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.error_current_password_equal_to_new_one
import chirp.feature.chat.presentation.generated.resources.error_current_password_incorrect
import chirp.feature.chat.presentation.generated.resources.error_invalid_file_type
import chirp.feature.chat.presentation.generated.resources.password_change_successful
import com.plcoding.core.designsystem.model.AvatarSizeUi
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.domain.validator.PasswordValidator
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.toStringRes
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.presentation.screen.user.profile.image.picker.ImagePickerResult
import com.plcoding.feature.chat.presentation.utils.FormatUtils.getInitials
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class UserProfileDialogScreenViewModel(
  private val chatRepository: ChatRepository,
  private val preferencesRepository: PreferencesRepository,
) : BaseScreenViewModel<UserProfileDialogScreenUiState>() {

  override fun getUiState(): UserProfileDialogScreenUiState {
    return UserProfileDialogScreenUiState()
  }

  override fun onInitialize() {
    super.onInitialize()

    observePasswords()
    observeLocalUser()
    syncLocalUser()
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

  private fun observeLocalUser() {
    preferencesRepository
      .observeAuthInfo()
      .filterNotNull()
      .onEach {
        updateUiState {
          copy(
            username = it.user.username,
            avatarUi = AvatarUi(
              initials = getInitials(it.user.username),
              imageUrl = it.user.profilePictureUrl,
              avatarSizeUi = AvatarSizeUi.LARGE,
            ),
            emailTextFieldState = TextFieldState(initialText = it.user.email)
          )
        }
      }
      .launchIn(viewModelScope)
  }

  private fun syncLocalUser() {
    viewModelScope.launch {
      chatRepository.syncLocalUser()
    }
  }

  fun handleAction(action: UserProfileDialogScreenAction) {
    when (action) {
      UserProfileDialogScreenAction.OnCloseClick -> TODO()
      UserProfileDialogScreenAction.OnCurrentPasswordEyeClick -> updateUiState {
        copy(isCurrentPasswordSecureMode = !isCurrentPasswordSecureMode)
      }
      UserProfileDialogScreenAction.OnDeleteImageClick -> {
        deleteProfileImage()
      }
      UserProfileDialogScreenAction.OnNewPasswordEyeClick -> updateUiState {
        copy(isNewPasswordSecureMode = !isNewPasswordSecureMode)
      }
      UserProfileDialogScreenAction.OnPrimaryButtonClick -> {
        changePassword()
      }
      UserProfileDialogScreenAction.OnSecondaryButtonClick -> TODO()
      is UserProfileDialogScreenAction.OnImagePicked -> {
        uploadProfileImage(action.result)
      }
      else -> Unit
    }
  }

  private fun changePassword() {
    launchLoadable {
      chatRepository.changePassword(
        oldPassword = screenUiState.value.uiState.currentPasswordTextFieldState.text.toString(),
        newPassword = screenUiState.value.uiState.newPasswordTextFieldState.text.toString(),
      ).onSuccess {
        screenUiState.value.uiState.currentPasswordTextFieldState.clearText()
        screenUiState.value.uiState.newPasswordTextFieldState.clearText()
        showSnackbar(Res.string.password_change_successful)
      }.onFailure {
        val messageRes = when (it) {
          DataError.Remote.UNAUTHORIZED -> Res.string.error_current_password_incorrect
          DataError.Remote.CONFLICT -> Res.string.error_current_password_equal_to_new_one
          else -> it.toStringRes()
        }
        showSnackbar(messageRes)
      }
    }
  }


  private fun uploadProfileImage(imagePickerResult: ImagePickerResult) {
    val byteArray = imagePickerResult.byteArray
      ?: return showSnackbar(Res.string.error_invalid_file_type)
    val mimeType = imagePickerResult.mimeType
      ?: return showSnackbar(Res.string.error_invalid_file_type)

    launchLoadable {
      chatRepository
        .uploadProfileImage(byteArray, mimeType)
        .onFailure { showSnackbar(it.toStringRes()) }
    }
  }

  private fun deleteProfileImage() {
    launchLoadable {
      chatRepository
        .deleteProfileImage()
        .onFailure { showSnackbar(it.toStringRes()) }
    }
  }
}
