package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.ChatMessageAm
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMessage

class ChatsAndMembersEntityMapper : Mapper<List<Chat>, List<ChatAndMemberEntity>, Unit> {

  override fun map(
    from: List<Chat>,
    params: Unit
  ): List<ChatAndMemberEntity> {
    return from.flatMap { chat ->
      chat.members.map {
        ChatAndMemberEntity(
          chatId = chat.id,
          memberId = it.userId,
        )
      }
    }
  }
}
