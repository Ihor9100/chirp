package com.plcoding.feature.chat.data.mapper

import com.plcoding.feature.chat.data.model.ChatMemberDto
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.domain.model.ChatMember

fun ChatMemberDto.toDomain(): ChatMember = ChatMember(
  userId = userId,
  username = username,
  profilePictureUrl = profilePictureUrl,
)

fun ChatMemberDto.toEntity(): ChatMemberEntity = ChatMemberEntity(
  id = userId,
  name = username,
  avatarUrl = profilePictureUrl,
)

fun ChatMemberEntity.toDomain(): ChatMember = ChatMember(
  userId = id,
  username = name,
  profilePictureUrl = avatarUrl,
)
