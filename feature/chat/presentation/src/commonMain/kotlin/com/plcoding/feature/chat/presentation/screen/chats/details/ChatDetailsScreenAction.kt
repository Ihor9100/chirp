package com.plcoding.feature.chat.presentation.screen.chats.details

import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.feature.chat.presentation.screen.user.profile.image.picker.ImagePickerResult

sealed interface ChatDetailsScreenAction {
  data object OnBackClick : ChatDetailsScreenAction
  data object OnMenuClick : ChatDetailsScreenAction
  data object OnMenuDismiss : ChatDetailsScreenAction
  data class OnMenuItemClick(val dropDownItemPm: DropDownItemUi) : ChatDetailsScreenAction
  data class OnMessageLongClick(val messageId: String) : ChatDetailsScreenAction
  data object OnMessageMenuDismiss : ChatDetailsScreenAction
  data class OnMessageMenuItemClick(val dropDownItemPm: DropDownItemUi) : ChatDetailsScreenAction
  data class OnMessageRetryClick(val messageId: String) : ChatDetailsScreenAction
  data class OnScroll(val lazyListScrollInfo: LazyListScrollInfo) : ChatDetailsScreenAction
  data object OnScrollToStartClick : ChatDetailsScreenAction
  data object OnPageRetryClick : ChatDetailsScreenAction
  data object OnSendClick : ChatDetailsScreenAction
  data class OnPhotosPicked(val results: List<ImagePickerResult>) : ChatDetailsScreenAction
  data object OnAddPhotoClick : ChatDetailsScreenAction
  data class OnRemoveSelectedPhotoClick(val photoId: String) : ChatDetailsScreenAction
  data class OnSelectedPhotoClick(val photoId: String) : ChatDetailsScreenAction
  data class OnMessageImageClick(val url: String) : ChatDetailsScreenAction
  data object OnImagePreviewDismiss : ChatDetailsScreenAction
}
