package com.plcoding.feature.chat.database.relation

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity

data class ChatAndMembersAndMessagesRelation(
  @Embedded
  val chatEntity: ChatEntity,
  @Relation(
    parentColumn = "id",
    entityColumn = "id",
    associateBy = Junction(
      value = ChatAndMemberEntity::class,
      parentColumn = "chatId",
      entityColumn = "memberId",
    ),
  )
  val chatMemberEntities: List<ChatMemberEntity>,
  @Relation(
    parentColumn = "id",
    entityColumn = "chatId",
    entity = ChatMessageEntity::class,
  )
  val chatMessageAndSenderRelations: List<ChatMessageAndSenderRelation>,
)
