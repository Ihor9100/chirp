package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatMembersAm(
  val userIds: List<String>,
)
