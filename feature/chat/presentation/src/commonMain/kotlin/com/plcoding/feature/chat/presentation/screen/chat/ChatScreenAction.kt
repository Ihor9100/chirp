package com.plcoding.feature.chat.presentation.screen.chat

sealed interface ChatScreenAction {
  data class OnChatClick(val chatId: String) : ChatScreenAction
}