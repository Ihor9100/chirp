package com.plcoding.feature.chat.presentation.screen.chats.details

import com.plcoding.core.designsystem.model.DropDownItemUi

sealed interface ChatDetailsScreenAction {
  data object OnChatDetailsBackClick : ChatDetailsScreenAction
  data object OnChatDetailsMenuClick : ChatDetailsScreenAction
  data object OnChatDetailsMenuDismissClick : ChatDetailsScreenAction
  data class OnChatDetailsMenuItemClick(val dropDownItemPm: DropDownItemUi) : ChatDetailsScreenAction
}