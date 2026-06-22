package com.plcoding.feature.chat.domain.model

data class ChatDetails(
  val chat: Chat,
  val chatMessagesAndMembers: List<ChatMessageAndMember>,
)
