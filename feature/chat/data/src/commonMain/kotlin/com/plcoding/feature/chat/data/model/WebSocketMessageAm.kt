package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class WebSocketMessageAm(
  val type: String,
  val payload: String,
) {

  companion object {
    fun from(
      webSocketPayloadAm: WebSocketPayloadAm,
      json: Json,
    ): WebSocketMessageAm {
      return WebSocketMessageAm(
        type = webSocketPayloadAm.webSocketMessageTypeAm.name,
        payload = when (webSocketPayloadAm) {
          is WebSocketPayloadAm.ChatMembersChangedAm -> json.encodeToString(webSocketPayloadAm)
          is WebSocketPayloadAm.MessageDeletedAm -> json.encodeToString(webSocketPayloadAm)
          is WebSocketPayloadAm.NewMessageAm -> json.encodeToString(webSocketPayloadAm)
          is WebSocketPayloadAm.ProfilePictureUpdatedAm -> json.encodeToString(webSocketPayloadAm)
        },
      )
    }
  }
}
