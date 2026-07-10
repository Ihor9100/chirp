package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.WebSocketMessageAm
import com.plcoding.feature.chat.data.model.WebSocketPayloadAm
import kotlinx.serialization.json.Json

class WebSocketMessageAmMapper(
  private val json: Json,
) : Mapper<WebSocketPayloadAm, WebSocketMessageAm> {

  override fun map(from: WebSocketPayloadAm): WebSocketMessageAm {
    return with(from) {
      WebSocketMessageAm(
        type = from.webSocketMessageTypeAm.name,
        payload = when (from) {
          is WebSocketPayloadAm.ChatMembersChangedAm -> json.encodeToString(from)
          is WebSocketPayloadAm.MessageDeletedAm -> json.encodeToString(from)
          is WebSocketPayloadAm.NewMessageAm -> json.encodeToString(from)
          is WebSocketPayloadAm.ProfilePictureUpdatedAm -> json.encodeToString(from)
        }
      )
    }
  }
}
