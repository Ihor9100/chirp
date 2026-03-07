package com.plcoding.feature.chat.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatMemberEntity(
  @PrimaryKey val id: String,
  val name: String,
  val avatarUrl: String,
)
