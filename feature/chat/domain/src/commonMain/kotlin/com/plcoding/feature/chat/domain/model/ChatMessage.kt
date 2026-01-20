package com.plcoding.feature.chat.domain.model

data class ChatMessage(
  val id: String,
  val chatId: String,
  val senderId: String,
  val content: String,
  val createdAt: String,
)
