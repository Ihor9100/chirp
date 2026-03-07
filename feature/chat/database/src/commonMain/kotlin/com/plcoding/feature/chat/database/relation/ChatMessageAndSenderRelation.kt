package com.plcoding.feature.chat.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity

data class ChatMessageAndSenderRelation(
  @Embedded
  val chatMessageEntity: ChatMessageEntity,
  @Relation(
    parentColumn = "senderId",
    entityColumn = "id",
  )
  val chatMemberEntity: ChatMemberEntity,
)
