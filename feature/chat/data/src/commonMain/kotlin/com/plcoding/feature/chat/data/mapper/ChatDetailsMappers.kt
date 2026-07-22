package com.plcoding.feature.chat.data.mapper

import com.plcoding.feature.chat.database.relation.ChatAndMembersAndMessagesRelation
import com.plcoding.feature.chat.domain.model.ChatDetails

fun ChatAndMembersAndMessagesRelation.toDomain(): ChatDetails {
  val sorted = chatMessageAndMemberRelations
    .sortedByDescending { it.chatMessageEntity.timestamp }

  return ChatDetails(
    chat = chatEntity.toDomain(
      chatMemberEntities = chatMemberEntities,
      chatLastMessage = sorted.firstOrNull()?.chatMessageEntity,
    ),
    chatMessagesAndMembers = chatMessageAndMemberRelations
      .map { it.toDomain() }
      .sortedByDescending { it.chatMessage.createdAt },
  )
}
