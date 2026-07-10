package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.WebSocketPayloadAm
import com.plcoding.feature.chat.domain.model.ChatMessage

class NewMessageAmMapper : Mapper<ChatMessage, WebSocketPayloadAm.NewMessageAm> {

  override fun map(from: ChatMessage): WebSocketPayloadAm.NewMessageAm {
    return with(from) {
      WebSocketPayloadAm.NewMessageAm(
        id = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        createdAt = createdAt.toString(),
      )
    }
  }
}
