package com.plcoding.feature.chat.presentation.screen.chats

import androidx.lifecycle.SavedStateHandle
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.feature.chat.domain.model.Chat

class ChatsScreenViewModel(
  private val savedStateHandle: SavedStateHandle,
) : BaseScreenViewModel<ChatsScreenContent>() {

  override fun getInitialContent(): ChatsScreenContent {
    return ChatsScreenContent()
  }

  fun onAction(action: ChatsScreenAction) {
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
