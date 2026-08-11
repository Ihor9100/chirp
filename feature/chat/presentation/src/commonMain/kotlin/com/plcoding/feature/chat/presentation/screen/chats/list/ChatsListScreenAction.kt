package com.plcoding.feature.chat.presentation.screen.chats.list

import com.plcoding.core.designsystem.model.DropDownItemUi

sealed interface ChatsListScreenAction {
  data object OnUserAvatarClick : ChatsListScreenAction
  data object OnLogoutClick : ChatsListScreenAction
  data class OnDropDownMenuItemClick(val dropDownItemUi: DropDownItemUi) : ChatsListScreenAction
  data object OnDropDownMenuDismiss : ChatsListScreenAction
  data class OnChatClick(val chatId: String?) : ChatsListScreenAction
  data object OnPlusClick : ChatsListScreenAction
}