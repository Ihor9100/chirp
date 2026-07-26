package com.plcoding.feature.chat.presentation.screen.user.profile

sealed interface UserProfileDialogScreenAction {
  data object OnCloseClick : UserProfileDialogScreenAction
  data object OnUploadImageClick : UserProfileDialogScreenAction
  data object OnDeleteClick : UserProfileDialogScreenAction
  data object OnCurrentPasswordEyeClick : UserProfileDialogScreenAction
  data object OnNewPasswordEyeClick : UserProfileDialogScreenAction
  data object OnPrimaryButtonClick : UserProfileDialogScreenAction
  data object OnSecondaryButtonClick : UserProfileDialogScreenAction
}