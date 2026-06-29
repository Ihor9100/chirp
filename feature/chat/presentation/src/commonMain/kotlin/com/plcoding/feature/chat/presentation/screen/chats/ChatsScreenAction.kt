package com.plcoding.feature.chat.presentation.screen.chats

sealed interface ChatsScreenAction {
  data class OnChatClick(val chatId: String) : ChatsScreenAction
  data object OnPlusClick : ChatsScreenAction
  data object OnChatDetailsBackClick : ChatsScreenAction
  data object OnChatDetailsOptionsClick : ChatsScreenAction
}