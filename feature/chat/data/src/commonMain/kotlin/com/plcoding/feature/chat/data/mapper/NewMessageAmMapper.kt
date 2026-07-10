package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.BiMapper
import com.plcoding.feature.chat.data.model.WebSocketPayloadAm
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlin.time.Instant

class NewMessageAmMapper : BiMapper<ChatMessage, WebSocketPayloadAm.NewMessageAm> {

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

  override fun reverse(from: WebSocketPayloadAm.NewMessageAm): ChatMessage {
    return with(from) {
      ChatMessage(
        id = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        createdAt = Instant.parse(createdAt),
        deliveryStatus = ChatMessageDeliveryStatus.SENT,
      )
    }
  }
}