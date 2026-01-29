package com.plcoding.feature.chat.presentation.screen.chats

import androidx.lifecycle.SavedStateHandle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.feature.chat.domain.model.Chat

class ChatsScreenViewModel(
  private val savedStateHandle: SavedStateHandle,
) : BaseScreenViewModel<ChatsScreenContentPm>() {

  override fun getContentPm(): ChatsScreenContentPm {
    return ChatsScreenContentPm.mock
  }

  fun onAction(onDismiss: () -> Unit) {
    showSnackbar(Res.string.group_chat) {
      onDismiss()
    }
  }

  fun onResult(chat: Chat?) {
    updateContentPm {
      copy(
        chat = chat,
      )
    }
  }
}
