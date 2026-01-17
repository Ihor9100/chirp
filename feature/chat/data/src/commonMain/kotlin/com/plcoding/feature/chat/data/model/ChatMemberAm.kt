package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatMemberAm(
  val userId: String,
  val username: String,
  val profilePictureUrl: String?
)
