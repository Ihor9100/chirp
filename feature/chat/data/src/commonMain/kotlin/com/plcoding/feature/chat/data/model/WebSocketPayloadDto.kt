package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface WebSocketPayloadDto {
  val messageType: WebSocketMessageType

  @Serializable
  data class NewMessageDto(
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val createdAt: String,
    override val messageType: WebSocketMessageType = WebSocketMessageType.NEW_MESSAGE,
  ) : WebSocketPayloadDto

  @Serializable
  data class MessageDeletedDto(
    val messageId: String,
    val chatId: String,
    override val messageType: WebSocketMessageType = WebSocketMessageType.MESSAGE_DELETED,
  ) : WebSocketPayloadDto

  @Serializable
  data class ProfilePictureUpdatedDto(
    val userId: String,
    val newUrl: String?,
    override val messageType: WebSocketMessageType = WebSocketMessageType.PROFILE_PICTURE_UPDATED,
  ) : WebSocketPayloadDto

  @Serializable
  data class ChatMembersChangedDto(
    val chatId: String,
    override val messageType: WebSocketMessageType = WebSocketMessageType.CHAT_PARTICIPANTS_CHANGED,
  ) : WebSocketPayloadDto
}
