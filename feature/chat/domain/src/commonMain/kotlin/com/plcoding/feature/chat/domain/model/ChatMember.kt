package com.plcoding.feature.chat.domain.model

import kotlinx.serialization.Serializable

data class ChatMember(
  val userId: String,
  val username: String,
  val profilePictureUrl: String?
)
