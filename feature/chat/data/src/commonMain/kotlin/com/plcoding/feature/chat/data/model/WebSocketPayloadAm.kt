package com.plcoding.feature.chat.data.model

import com.plcoding.feature.chat.domain.model.ChatMessage
import kotlinx.serialization.Serializable

@Serializable
sealed class WebSocketPayloadAm(
  val webSocketMessageTypeAm: WebSocketMessageTypeAm,
) {

  @Serializable
  data class NewMessageAm(
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val createdAt: String,
  ) : WebSocketPayloadAm(WebSocketMessageTypeAm.NEW_MESSAGE)

  @Serializable
  data class MessageDeletedAm(
    val messageId: String,
    val chatId: String,
  ) : WebSocketPayloadAm(WebSocketMessageTypeAm.MESSAGE_DELETED)

  @Serializable
  data class ProfilePictureUpdatedAm(
    val userId: String,
    val newUrl: String?,
  ) : WebSocketPayloadAm(WebSocketMessageTypeAm.PROFILE_PICTURE_UPDATED)

  @Serializable
  data class ChatMembersChangedAm(
    val chatId: String,
  ) : WebSocketPayloadAm(WebSocketMessageTypeAm.CHAT_PARTICIPANTS_CHANGED)
}
