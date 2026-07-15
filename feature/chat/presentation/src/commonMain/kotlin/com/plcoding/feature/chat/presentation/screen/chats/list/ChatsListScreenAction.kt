package com.plcoding.feature.chat.presentation.screen.chats.list

sealed interface ChatsListScreenAction {
  data class OnChatClick(val chatId: String?) : ChatsListScreenAction
  data object OnPlusClick : ChatsListScreenAction
}