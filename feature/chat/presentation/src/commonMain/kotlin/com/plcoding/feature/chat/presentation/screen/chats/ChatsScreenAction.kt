package com.plcoding.feature.chat.presentation.screen.chats

import com.plcoding.core.designsystem.model.DropDownItemUi
import org.jetbrains.compose.resources.StringResource

sealed interface ChatsScreenAction {
  data class OnChatClick(val chatId: String) : ChatsScreenAction
  data object OnPlusClick : ChatsScreenAction
  data object OnChatDetailsBackClick : ChatsScreenAction
  data object OnChatDetailsMenuClick : ChatsScreenAction
  data object OnChatDetailsMenuDismissClick : ChatsScreenAction
  data class OnChatDetailsMenuItemClick(val dropDownItemPm: DropDownItemUi) : ChatsScreenAction
}