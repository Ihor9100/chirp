package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
  val id: String,
  val participants: List<ChatMemberDto>,
  val lastActivityAt: String,
  val lastMessage: ChatMessageDto?,
)
