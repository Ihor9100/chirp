package com.plcoding.feature.chat.presentation.screen.chats.details

import com.plcoding.core.designsystem.model.DropDownItemUi

sealed interface ChatDetailsScreenAction {
  data object OnBackClick : ChatDetailsScreenAction
  data object OnMenuClick : ChatDetailsScreenAction
  data object OnMenuDismissClick : ChatDetailsScreenAction
  data class OnMenuItemClick(val dropDownItemPm: DropDownItemUi) : ChatDetailsScreenAction
  data class OnRetryClick(val messageId: String) : ChatDetailsScreenAction
  data object OnSendClick : ChatDetailsScreenAction
}