package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.ChatAm
import com.plcoding.feature.chat.domain.model.Chat
import kotlin.time.Instant

class ChatMapper(
  private val chatMemberAmMapper: ChatMemberAmMapper,
  private val chatMessageMapper: ChatMessageMapper,
) : Mapper<ChatAm, Chat, Unit> {

  override fun map(from: ChatAm, params: Unit): Chat {
    return with(from) {
      Chat(
        id = id,
        members = chatMemberAmMapper.map(participants, Unit),
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.let { chatMessageMapper.map(it, Unit) },
      )
    }
  }
}
