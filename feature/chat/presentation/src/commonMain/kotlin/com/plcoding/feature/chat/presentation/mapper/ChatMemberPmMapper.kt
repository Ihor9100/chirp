package com.plcoding.feature.chat.presentation.mapper

import com.plcoding.core.designsystem.components.AvatarPm
import com.plcoding.core.designsystem.components.AvatarSize
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.presentation.model.ChatMemberPm

class ChatMemberPmMapper : Mapper<ChatMember, ChatMemberPm, Unit> {

  override fun map(from: ChatMember, params: Unit): ChatMemberPm {
    return with(from) {
      ChatMemberPm(
        id = userId,
        fullName = username,
        avatarPm = AvatarPm(
          initials = getInitials(username),
          imageUrl = profilePictureUrl,
          avatarSize = AvatarSize.MEDIUM,
        ),
      )
    }
  }

  fun getInitials(fullName: String): String {
    if (fullName.isBlank()) return "?"

    return fullName
      .split(" ")
      .take(2)
      .joinToString(
        separator = "",
        transform = { it.first().uppercase() },
      )
  }
}
