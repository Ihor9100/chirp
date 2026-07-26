package com.plcoding.feature.chat.presentation.screen.user.profile

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.designsystem.model.AvatarUi

data class UserProfileDialogScreenUiState(
  val username: String = "",
  val avatarUi: AvatarUi? = null,
  val isImageLoading: Boolean = false,
  val emailTextFieldState: TextFieldState = TextFieldState(),
  val currentPasswordTextFieldState: TextFieldState = TextFieldState(),
  val newPasswordTextFieldState: TextFieldState = TextFieldState(),
  val isCurrentPasswordSecureMode: Boolean = false,
  val isNewPasswordVisible: Boolean = false,
)