package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatAm(
  val id: String,
  val participants: List<ChatMemberAm>,
  val lastActivityAt: String,
  val lastMessage: ChatMessageAm?
)
