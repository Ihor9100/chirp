package com.plcoding.feature.chat.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatEntity(
  @PrimaryKey val id: String,
  val lastMessage: String?,
  val lastActivityAt: Long,
)
