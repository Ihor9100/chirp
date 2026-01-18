package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.ChatMemberAm
import com.plcoding.feature.chat.domain.model.ChatMember

class ChatMemberMapper() : Mapper<ChatMemberAm, ChatMember, Unit> {

  override fun map(from: ChatMemberAm, params: Unit): ChatMember {
    return with(from) {
      ChatMember(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl,
      )
    }
  }
}
