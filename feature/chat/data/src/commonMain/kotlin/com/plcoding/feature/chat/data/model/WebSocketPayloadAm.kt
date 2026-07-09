package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface WebSocketPayloadAm {

  @Serializable
  data class NewMessageAm(
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val createdAt: String
  ) : WebSocketPayloadAm

  @Serializable
  data class MessageDeletedAm(
    val messageId: String,
    val chatId: String
  ) : WebSocketPayloadAm

  @kotlinx.serialization.Serializable
  data class ProfilePictureUpdatedAm(
    val userId: String,
    val newUrl: String?,
  ) : WebSocketPayloadAm

  @Serializable
  data class ChatParticipantsChangedAm(
    val chatId: String,
  ) : WebSocketPayloadAm
}
