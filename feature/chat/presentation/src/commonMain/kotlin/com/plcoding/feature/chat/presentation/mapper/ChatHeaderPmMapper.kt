package com.plcoding.feature.chat.presentation.mapper

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.you_and_others
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.model.ChatMember.Companion.isGroup
import com.plcoding.feature.chat.presentation.model.ChatHeaderPm

class ChatHeaderPmMapper(
  private val chatMemberPmMapper: ChatMemberPmMapper,
) : Mapper<ChatHeaderPmMapper.From, ChatHeaderPm> {

  override fun map(from: From): ChatHeaderPm {
    return with(from) {
      ChatHeaderPm(
        avatarsPm = chatMembers.map {
          val from = ChatMemberPmMapper.From(it, isInChat = false)
          chatMemberPmMapper.map(from).avatarPm
        },
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
