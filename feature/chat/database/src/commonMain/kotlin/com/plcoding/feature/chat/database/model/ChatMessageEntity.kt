package com.plcoding.feature.chat.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatMessageEntity(
  @PrimaryKey val id: String,
  val chatId: String,
  val senderId: String,
  val content: String,
  val timestamp: Long,
  val status: String,
)
