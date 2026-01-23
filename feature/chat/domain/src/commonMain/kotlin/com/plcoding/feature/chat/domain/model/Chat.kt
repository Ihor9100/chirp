package com.plcoding.feature.chat.domain.model

data class  Chat(
  val id: String,
  val members: List<ChatMember>,
  val lastActivityAt: String,
  val lastMessage: ChatMessage?
) {

  val isGroup = members.size > 2
}
