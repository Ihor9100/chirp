package com.plcoding.feature.chat.presentation.screen.user.profile

import com.plcoding.feature.chat.presentation.screen.user.profile.image.picker.ImagePickerResult

sealed interface UserProfileDialogScreenAction {
  data object OnCloseClick : UserProfileDialogScreenAction
  data object OnUploadImageClick : UserProfileDialogScreenAction
  data class OnImagePicked(val result: ImagePickerResult) : UserProfileDialogScreenAction
  data object OnDeleteImageClick : UserProfileDialogScreenAction
  data object OnCurrentPasswordEyeClick : UserProfileDialogScreenAction
  data object OnNewPasswordEyeClick : UserProfileDialogScreenAction
  data object OnPrimaryButtonClick : UserProfileDialogScreenAction
  data object OnSecondaryButtonClick : UserProfileDialogScreenAction
}