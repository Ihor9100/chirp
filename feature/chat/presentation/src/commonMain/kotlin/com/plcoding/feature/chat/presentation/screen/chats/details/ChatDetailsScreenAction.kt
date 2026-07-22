package com.plcoding.feature.chat.presentation.screen.chats.details

import com.plcoding.core.designsystem.model.DropDownItemUi

sealed interface ChatDetailsScreenAction {
  data object OnBackClick : ChatDetailsScreenAction
  data object OnMenuClick : ChatDetailsScreenAction
  data object OnMenuDismiss : ChatDetailsScreenAction
  data class OnMenuItemClick(val dropDownItemPm: DropDownItemUi) : ChatDetailsScreenAction
  data class OnMessageLongClick(val messageId: String) : ChatDetailsScreenAction
  data object OnMessageMenuDismiss : ChatDetailsScreenAction
  data class OnMessageMenuItemClick(val dropDownItemPm: DropDownItemUi) : ChatDetailsScreenAction
  data class OnMessageRetryClick(val messageId: String) : ChatDetailsScreenAction
  data object OnLoadMore : ChatDetailsScreenAction
  data class OnScrollToStartChanged(val showScrollToStart: Boolean) : ChatDetailsScreenAction
  data object OnScrollToStartClick : ChatDetailsScreenAction
  data object OnPageRetryClick : ChatDetailsScreenAction
  data object OnSendClick : ChatDetailsScreenAction
}