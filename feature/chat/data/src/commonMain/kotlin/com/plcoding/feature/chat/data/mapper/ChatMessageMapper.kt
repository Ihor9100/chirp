package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.ChatMessageAm
import com.plcoding.feature.chat.domain.model.ChatMessage

class ChatMessageMapper : Mapper<ChatMessageAm, ChatMessage, Unit> {

  override fun map(from: ChatMessageAm, params: Unit): ChatMessage {
    return with(from) {
      ChatMessage(
        id = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        createdAt = createdAt,
      )
    }
  }
}
