package com.plcoding.feature.chat.domain.model

import kotlin.time.Clock
import kotlin.time.Instant

data class Chat(
  val id: String,
  val members: List<ChatMember>,
  val lastActivityAt: Instant,
  val lastMessage: ChatMessage?
) {

  companion object {
    val mocks
      get() = List(10) { index ->
        Chat(
          id = "$index",
          members = ChatMember.mocks,
          lastActivityAt = Clock.System.now(),
          lastMessage = ChatMessage.mock,
        )
      }
  }
}
