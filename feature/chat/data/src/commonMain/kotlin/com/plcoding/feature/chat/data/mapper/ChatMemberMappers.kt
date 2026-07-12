package com.plcoding.feature.chat.data.mapper

import com.plcoding.feature.chat.data.model.ChatMemberAm
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.domain.model.ChatMember

fun ChatMemberAm.toDomain(): ChatMember = ChatMember(
  userId = userId,
  username = username,
  profilePictureUrl = profilePictureUrl,
)

fun ChatMemberAm.toEntity(): ChatMemberEntity = ChatMemberEntity(
  id = userId,
  name = username,
  avatarUrl = profilePictureUrl,
)

fun ChatMemberEntity.toDomain(): ChatMember = ChatMember(
  userId = id,
  username = name,
  profilePictureUrl = avatarUrl,
)
