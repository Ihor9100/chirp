package com.plcoding.feature.chat.presentation.screen.chats

import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.model.ChatPm

data class ChatsScreenContent(
  val chatId: String? = null,
  val chat: Chat? = null,
  val chatsPm: List<ChatPm> = emptyList(),
) {

  companion object {
    val mock
      get() = ChatsScreenContent(
        chatsPm = ChatPm.mocks
      )
  }
}
