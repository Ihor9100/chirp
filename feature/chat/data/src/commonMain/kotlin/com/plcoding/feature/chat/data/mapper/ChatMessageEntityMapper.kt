package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.BiMapper
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.ChatMessageAm
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlin.time.Instant

class ChatMessageEntityMapper : BiMapper<ChatMessage, ChatMessageEntity> {

  override fun map(from: ChatMessage): ChatMessageEntity {
    return with(from) {
      ChatMessageEntity(
        id = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        timestamp = createdAt.toEpochMilliseconds(),
        status = from.deliveryStatus.name,
      )
    }
  }

  override fun reverse(from: ChatMessageEntity): ChatMessage {
    return with(from) {
      ChatMessage(
        id = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        createdAt = Instant.fromEpochMilliseconds(timestamp),
        deliveryStatus = ChatMessageDeliveryStatus.valueOf(status),
      )
    }
  }
}
