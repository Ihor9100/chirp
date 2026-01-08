package com.plcoding.feature.chat.presentation.screen.chat

import com.plcoding.core.presentation.screen.base.BaseScreenViewModel

class ChatScreenViewModel() : BaseScreenViewModel<ChatScreenContent>() {

  override fun getInitialContent(): ChatScreenContent {
    return ChatScreenContent()
  }

  fun onAction(action: ChatScreenAction) {
    when (action) {
      is ChatScreenAction.OnChatClick -> updateContent {
        copy(chatId = action.chatId)
      }
    }
  }
}
