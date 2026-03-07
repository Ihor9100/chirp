package com.plcoding.feature.chat.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
  primaryKeys = ["chatId, memberId"],
  foreignKeys = [
    ForeignKey(
      entity = ChatEntity::class,
      parentColumns = ["id"],
      childColumns = ["chatId"],
      onDelete = ForeignKey.CASCADE,
    ),
    ForeignKey(
      entity = ChatMemberEntity::class,
      parentColumns = ["id"],
      childColumns = ["memberId"],
      onDelete = ForeignKey.CASCADE,
    ),
  ]
)
data class ChatAndMemberEntity(
  val chatId: String,
  val memberId: String,
)
