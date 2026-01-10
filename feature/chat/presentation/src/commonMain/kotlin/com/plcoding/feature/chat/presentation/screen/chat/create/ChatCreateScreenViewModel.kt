package com.plcoding.feature.chat.presentation.screen.chat.create

import com.plcoding.core.presentation.screen.base.BaseScreenViewModel

class ChatCreateScreenViewModel : BaseScreenViewModel<ChatCreateScreenContent>() {

  override fun getInitialContent(): ChatCreateScreenContent {
    return ChatCreateScreenContent()
  }

  fun onAction(action: ChatCreateScreenAction) {
    when (action) {
      else -> TODO("Handle actions")
    }
  }
}