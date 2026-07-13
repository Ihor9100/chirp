package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatMembersDto(
  val userIds: List<String>,
)
