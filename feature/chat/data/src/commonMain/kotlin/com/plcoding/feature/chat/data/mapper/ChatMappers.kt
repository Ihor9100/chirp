package com.plcoding.feature.chat.data.mapper

import com.plcoding.feature.chat.data.model.ChatAm
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.relation.ChatAndMembersRelation
import com.plcoding.feature.chat.domain.model.Chat
import kotlin.time.Instant

fun ChatAm.toDomain(): Chat = Chat(
  id = id,
  members = participants.map { it.toDomain() },
  lastActivityAt = Instant.parse(lastActivityAt),
  lastMessage = lastMessage?.toDomain(),
)

fun ChatAm.toEntity(): ChatEntity = ChatEntity(
  id = id,
  lastActivityAt = Instant.parse(lastActivityAt).toEpochMilliseconds(),
)

fun ChatAm.toEntities(): List<ChatAndMemberEntity> = participants.map {
  ChatAndMemberEntity(chatId = id, memberId = it.userId)
}

fun ChatEntity.toDomain(
  chatMemberEntities: List<ChatMemberEntity>,
  chatLastMessage: ChatMessageEntity?,
): Chat = Chat(
  id = id,
  members = chatMemberEntities.map { it.toDomain() },
  lastActivityAt = Instant.fromEpochMilliseconds(lastActivityAt),
  lastMessage = chatLastMessage?.toDomain(),
)

fun ChatAndMembersRelation.toDomain(): Chat = Chat(
  id = chatEntity.id,
  members = chatMemberEntities.map { it.toDomain() },
  lastActivityAt = Instant.fromEpochMilliseconds(chatEntity.lastActivityAt),
  lastMessage = chatLastMessageView?.toDomain(),
)
