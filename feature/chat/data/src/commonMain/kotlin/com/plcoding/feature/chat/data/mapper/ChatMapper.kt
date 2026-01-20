package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.ChatAm
import com.plcoding.feature.chat.domain.model.Chat

class ChatMapper(
  private val chatMemberMapper: ChatMemberMapper,
  private val chatMessageMapper: ChatMessageMapper,
) : Mapper<ChatAm, Chat, Unit> {

  override fun map(from: ChatAm, params: Unit): Chat {
    return with(from) {
      Chat(
        id = id,
        members = chatMemberMapper.map(participants, Unit),
        lastActivityAt = lastActivityAt,
        lastMessage = lastMessage?.let { chatMessageMapper.map(it, Unit) },
      )
    }
  }
}
