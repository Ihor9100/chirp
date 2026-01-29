package com.plcoding.feature.chat.presentation.screen.chats

sealed interface ChatsScreenAction {
  data class OnChatsClick(val chatId: String) : ChatsScreenAction
  data object OnChatsCreateClick : ChatsScreenAction
}