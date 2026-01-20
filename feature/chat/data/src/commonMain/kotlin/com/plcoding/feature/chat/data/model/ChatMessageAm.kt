package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageAm(
  val id: String,
  val chatId: String,
  val senderId: String,
  val content: String,
  val createdAt: String,
)
