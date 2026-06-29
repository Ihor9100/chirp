package com.plcoding.feature.chat.presentation.mapper

import chirp.core.designsystem.generated.resources.ic_users
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.core.designsystem.generated.resources.Res as CoreRes
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.ic_log_out
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.you_and_others
import com.plcoding.core.designsystem.model.DropDownItemPm
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.model.ChatMember.Companion.isGroup
import com.plcoding.feature.chat.presentation.model.ChatHeaderPm
import com.plcoding.feature.chat.presentation.model.ChatMemberPm

class ChatHeaderPmMapper(
  private val chatMemberPmMapper: ChatMemberPmMapper,
) : Mapper<ChatHeaderPmMapper.From, ChatHeaderPm> {

  override fun map(from: From): ChatHeaderPm {
    return with(from) {
      ChatHeaderPm(
        avatarsPm = chatMemberPmMapper
          .mapList(chatMembers)
          .map(ChatMemberPm::avatarPm),
        title = getTitle(this),
        description = getDescription(this),
      )
    }
  }

  private fun getTitle(from: From): TextProvider {
    return if (from.chatMembers.isGroup) {
      TextProvider.Resource(Res.string.group_chat, listOf())
    } else {
      val member = from
        .chatMembers
        .firstOrNull { it.userId != from.yourId }
        ?: from.chatMembers.first()

      TextProvider.Dynamic(member.username)
    }
  }

  private fun getDescription(from: From): TextProvider? {
    if (!from.chatMembers.isGroup) return null

    val remoteMembers = from.chatMembers.filter { it.userId != from.yourId }
    val formattedRemoteMembers = remoteMembers.joinToString { it.username }
    return TextProvider.Resource(Res.string.you_and_others, listOf(formattedRemoteMembers))
  }

  data class From(
    val yourId: String?,
    val chatMembers: List<ChatMember>,
  )
}
