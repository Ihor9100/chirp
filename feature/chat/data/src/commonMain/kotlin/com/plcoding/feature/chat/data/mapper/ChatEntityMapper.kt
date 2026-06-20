package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.BiMapper
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.view.ChatLastMessageView
import com.plcoding.feature.chat.domain.model.Chat
import kotlin.time.Instant

class ChatEntityMapper(
  private val chatMemberEntityMapper: ChatMemberEntityMapper,
  private val chatMessageEntityMapper: ChatMessageEntityMapper,
) : BiMapper<Chat, ChatEntity, ChatEntityMapper.Params> {

  override fun map(
    from: Chat,
    params: Params
  ): ChatEntity {
    return with(from) {
      ChatEntity(
        id = id,
        lastActivityAt = lastActivityAt.toEpochMilliseconds(),
      )
    }
  }

  override fun reverse(
    from: ChatEntity,
    params: Params
  ): Chat {
    return with(from) {
      Chat(
        id = id,
        members = chatMemberEntityMapper.reverse(chatMemberEntities, Unit),
        lastActivityAt = Instant.fromEpochMilliseconds(lastActivityAt),
        lastMessage = params?.chatLastMessage?.let { chatMessageEntityMapper.map(it, Unit) },
      )
    }
  }

  data class Params(
    val chatMemberEntities: List<ChatMemberEntity>,
    val chatLastMessage: ChatMessageEntity?,
  )
}
