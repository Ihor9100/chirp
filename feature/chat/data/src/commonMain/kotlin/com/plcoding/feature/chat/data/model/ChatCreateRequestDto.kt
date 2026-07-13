package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatCreateRequestDto(
  val otherUserIds: List<String>,
)
