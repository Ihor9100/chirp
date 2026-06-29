package com.plcoding.feature.chat.data.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.data.model.ChatMemberAm
import com.plcoding.feature.chat.domain.model.ChatMember

class ChatMemberAmMapper() : Mapper<ChatMemberAm, ChatMember> {

  override fun map(from: ChatMemberAm): ChatMember {
    return with(from) {
      ChatMember(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl,
      )
    }
  }
}
