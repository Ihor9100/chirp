package com.plcoding.feature.chat.presentation.screen.chats

import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.model.ChatDetailsPm
import com.plcoding.feature.chat.presentation.model.ChatEmptyStatePm
import com.plcoding.feature.chat.presentation.model.ChatHeaderPm
import com.plcoding.feature.chat.presentation.model.ChatPm

data class ChatsScreenContentPm(
  val chatsEmptyState: ChatEmptyStatePm?,
  val chatsPm: List<ChatPm>,
  val noChatEmptyState: ChatEmptyStatePm?,
  val chatId: String?,
  val chatEmptyState: ChatEmptyStatePm?,
  val chatHeaderPm: ChatHeaderPm?,
  val chatDetailsPm: List<ChatDetailsPm>?,
) {

  companion object {
    val mock
      get() = ChatsScreenContentPm(
        chatsEmptyState = ChatEmptyStatePm.mock,
        chatsPm = ChatPm.mocks,
        noChatEmptyState = ChatEmptyStatePm.mock,
        chatId = ChatPm.mocks.first().id,
        chatEmptyState = ChatEmptyStatePm.mock,
        chatHeaderPm = ChatHeaderPm.mock,
        chatDetailsPm = ChatDetailsPm.mocks,
      )
  }
}
