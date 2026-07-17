package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class WebSocketMessageDto(
  val type: String,
  val payload: String,
) {

  companion object {
    fun from(
      webSocketPayloadDto: WebSocketPayloadDto,
      json: Json,
    ): WebSocketMessageDto {
      return WebSocketMessageDto(
        type = webSocketPayloadDto.messageType.name,
        payload = when (webSocketPayloadDto) {
          is WebSocketPayloadDto.ChatMembersChangedDto -> json.encodeToString(webSocketPayloadDto)
          is WebSocketPayloadDto.MessageDeletedDto -> json.encodeToString(webSocketPayloadDto)
          is WebSocketPayloadDto.NewMessageDto -> json.encodeToString(webSocketPayloadDto)
          is WebSocketPayloadDto.ProfilePictureUpdatedDto -> json.encodeToString(webSocketPayloadDto)
        },
      )
    }
  }
}
