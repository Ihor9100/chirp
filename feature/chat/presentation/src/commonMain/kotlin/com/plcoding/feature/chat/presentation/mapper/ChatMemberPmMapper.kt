package com.plcoding.feature.chat.presentation.mapper

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.in_chat_member
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.designsystem.model.AvatarSizePm
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper.From
import com.plcoding.feature.chat.presentation.model.ChatMemberPm

class ChatMemberPmMapper : Mapper<From, ChatMemberPm> {

  override fun map(from: From): ChatMemberPm {
    return with(from.chatMember) {
      ChatMemberPm(
        id = userId,
        fullName = getFullName(from),
        avatarPm = AvatarPm(
          initials = getInitials(username),
          imageUrl = profilePictureUrl,
          avatarSizePm = AvatarSizePm.MEDIUM,
        ),
      )
    }
  }

  private fun getFullName(from: From): TextProvider {
    return if (from.isInChat) {
      TextProvider.Resource(Res.string.in_chat_member, listOf(from.chatMember.username))
    } else {
      TextProvider.Dynamic(from.chatMember.username)
    }
  }

  private fun getInitials(fullName: String): String {
    if (fullName.isBlank()) return "?"

    return fullName
      .split(" ")
      .take(2)
      .joinToString(
        separator = "",
        transform = { it.first().uppercase() },
      )
  }

  data class From(
    val chatMember: ChatMember,
    val isInChat: Boolean,
  )
}
