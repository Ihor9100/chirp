package com.plcoding.feature.chat.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
  foreignKeys = [
    ForeignKey(
      entity = ChatEntity::class,
      parentColumns = ["id"],
      childColumns = ["chatId"],
      onDelete = ForeignKey.CASCADE,
    )
  ]
)
data class ChatMessageEntity(
  @PrimaryKey val id: String,
  val chatId: String,
  val senderId: String,
  val content: String,
  val timestamp: Long,
  val status: String,
)
