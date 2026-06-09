package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.domain.model.Chat

class ChatEntityMapper : Mapper<Chat, ChatEntity, Unit> {

  override fun map(
    from: Chat,
    params: Unit
  ): ChatEntity {
    return with(from) {
      ChatEntity(
        id = id,
        lastActivityAt = lastActivityAt.toEpochMilliseconds(),
      )
    }
  }
}
