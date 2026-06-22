package com.plcoding.feature.chat.presentation.screen.chats

import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.model.ChatDetailsPm
import com.plcoding.feature.chat.presentation.model.ChatHeaderPm
import com.plcoding.feature.chat.presentation.model.ChatPm

data class ChatsScreenContentPm(
  val chatsPm: List<ChatPm>,
  val chatId: String?,
  val chatHeaderPm: ChatHeaderPm?,
  val chatDetailsPm: List<ChatDetailsPm>?,
) {

  companion object {
    val mock
      get() = ChatsScreenContentPm(
        chatsPm = ChatPm.mocks,
        chatId = ChatPm.mocks.first().id,
        chatHeaderPm = ChatHeaderPm.mock,
        chatDetailsPm = ChatDetailsPm.mocks,
      )
  }
}
