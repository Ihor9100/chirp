package com.plcoding.feature.chat.presentation.mapper

import chirp.feature.chat.presentation.generated.resources.Res
import com.plcoding.core.designsystem.components.AvatarPm
import com.plcoding.core.designsystem.components.AvatarSize
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import com.plcoding.feature.chat.presentation.model.ChatPm

class ChatPmMapper(
  private val chatMemberPmMapper: ChatMemberPmMapper,
) : Mapper<Chat, ChatPm, Unit> {

  override fun map(from: Chat, params: Unit): ChatPm {
    return with(from) {
      ChatPm(
        id = id,
        avatarsPm = chatMemberPmMapper
          .map(members, Unit)
          .map(ChatMemberPm::avatarPm),
        title = getTitle(this),
        description = getDescription(from),
        content = lastMessage?.content,
      )
    }
  }

  private fun getTitle(from: Chat): TextProvider {
    return if (from.members.size > 1) {
      TextProvider.Resource(Res.string.group_chat)
    } else {
      TextProvider.Dynamic(from.members.first().username)
    }
  }

  private fun getDescription(from: Chat): String? {
    if (from.members.size < 2) return null
    return from.members.joinToString { it.username }
  }
}
