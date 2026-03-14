package com.plcoding.feature.chat.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
 tableName = "chats"
)
data class ChatEntity(
  @PrimaryKey val id: String,
  val lastActivityAt: Long,
)
