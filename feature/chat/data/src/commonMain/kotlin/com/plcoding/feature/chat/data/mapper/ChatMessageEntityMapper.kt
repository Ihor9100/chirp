package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.ChatMessageAm
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.domain.model.ChatMessage

class ChatMessageEntityMapper : Mapper<ChatMessage, ChatMessageEntity, Unit> {

  override fun map(
    from: ChatMessage,
    params: Unit
  ): ChatMessageEntity {
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
}
