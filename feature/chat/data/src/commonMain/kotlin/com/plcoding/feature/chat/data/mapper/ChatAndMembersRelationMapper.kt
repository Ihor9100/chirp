package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.database.relation.ChatAndMembersRelation
import com.plcoding.feature.chat.database.view.ChatLastMessageView
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlin.time.Instant

class ChatAndMembersRelationMapper(
  private val chatMemberEntityMapper: ChatMemberEntityMapper
) : Mapper<ChatAndMembersRelation, Chat, Unit> {

  override fun map(
    from: ChatAndMembersRelation,
    params: Unit
  ): Chat {
    return with(from) {
      Chat(
        id = chatEntity.id,
        members = chatMemberEntityMapper.reverse(chatMemberEntities, Unit),
        lastActivityAt = Instant.fromEpochMilliseconds(chatEntity.lastActivityAt),
        lastMessage = chatLastMessageView?.let(::getLastMessage),
      )
    }
  }

  private fun getLastMessage(lastMessageView: ChatLastMessageView): ChatMessage {
    return with(lastMessageView) {
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
