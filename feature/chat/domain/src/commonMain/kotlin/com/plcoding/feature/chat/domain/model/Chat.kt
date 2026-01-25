package com.plcoding.feature.chat.domain.model

data class Chat(
  val id: String,
  val members: List<ChatMember>,
  val lastActivityAt: String,
  val lastMessage: ChatMessage?
) {

  companion object {
    val mocks
      get() = List(10) { index ->
        Chat(
          id = "$index",
          members = ChatMember.mocks,
          lastActivityAt = "lastActivityAt",
          lastMessage = ChatMessage.mock,
        )
      }
  }

  val isGroup = members.size > 2
}
