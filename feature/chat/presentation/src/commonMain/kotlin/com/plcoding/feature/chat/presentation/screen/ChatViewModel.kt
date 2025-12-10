package com.plcoding.feature.chat.presentation.screen

import com.plcoding.core.presentation.base.BaseViewModel

class ChatViewModel : BaseViewModel<ChatState>() {

  override fun getInitialState() = ChatState()

  fun onAction(action: ChatAction) {
    when (action) {
      else -> TODO("Handle actions")
    }
  }
}
