package com.plcoding.feature.chat.data.model

import com.plcoding.feature.chat.domain.model.ChatMessage
import kotlinx.serialization.Serializable

@Serializable
sealed class WebSocketPayloadDto(
  val webSocketMessageTypeDto: WebSocketMessageTypeDto,
) {

  @Serializable
  data class NewMessageDto(
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val createdAt: String,
  ) : WebSocketPayloadDto(WebSocketMessageTypeDto.NEW_MESSAGE)

  @Serializable
  data class MessageDeletedDto(
    val messageId: String,
    val chatId: String,
  ) : WebSocketPayloadDto(WebSocketMessageTypeDto.MESSAGE_DELETED)

  @Serializable
  data class ProfilePictureUpdatedDto(
    val userId: String,
    val newUrl: String?,
  ) : WebSocketPayloadDto(WebSocketMessageTypeDto.PROFILE_PICTURE_UPDATED)

  @Serializable
  data class ChatMembersChangedDto(
    val chatId: String,
  ) : WebSocketPayloadDto(WebSocketMessageTypeDto.CHAT_PARTICIPANTS_CHANGED)
}
