package com.plcoding.feature.chat.presentation.screen.chat

import androidx.lifecycle.SavedStateHandle
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.feature.chat.domain.model.Chat

class ChatScreenViewModel(
  private val savedStateHandle: SavedStateHandle,
) : BaseScreenViewModel<ChatScreenContent>() {

  override fun getInitialContent(): ChatScreenContent {
    return ChatScreenContent()
  }

  fun onAction(action: ChatScreenAction) {
    when (action) {
      else -> {
        println("lol arg = ${savedStateHandle.get<String>("arg")}")
      }
    }
  }

  fun onResult(chat: Chat?) {
    updateContent {
      copy(
        chat = chat,
      )
    }
  }
}
