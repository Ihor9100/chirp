package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.BiMapper
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.domain.model.ChatMember

class ChatMemberEntityMapper : BiMapper<ChatMember, ChatMemberEntity> {

  override fun map(from: ChatMember): ChatMemberEntity {
    return with(from) {
      ChatMemberEntity(
        id = userId,
        name = username,
        avatarUrl = profilePictureUrl,
      )
    }
  }

  override fun reverse(from: ChatMemberEntity): ChatMember {
    return with(from) {
      ChatMember(
        userId = id,
        username = name,
        profilePictureUrl = avatarUrl,
      )
    }
  }
}
