package com.plcoding.feature.chat.presentation.screen

sealed interface ChatScreenAction {
  data class OnChatClick(val chatId: String) : ChatScreenAction
}