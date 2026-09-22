package com.plcoding.feature.chat.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "chat_message_attachments",
  foreignKeys = [
    ForeignKey(
      entity = ChatMessageEntity::class,
      parentColumns = ["id"],
      childColumns = ["messageId"],
      onDelete = ForeignKey.CASCADE,
    )
  ],
  indices = [
    Index("messageId"),
  ],
)
data class ChatMessageAttachmentEntity(
  @PrimaryKey val id: String,
  val messageId: String,
  val url: String,
  val mimeType: String,
  val sizeBytes: Long,
  val type: String,
)
