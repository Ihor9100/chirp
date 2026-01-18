package com.plcoding.feature.chat.domain.model

data class ChatMember(
  val userId: String,
  val username: String,
  val profilePictureUrl: String?
)
