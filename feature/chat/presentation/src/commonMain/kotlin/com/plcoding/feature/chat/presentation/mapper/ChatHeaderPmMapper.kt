package com.plcoding.feature.chat.presentation.mapper

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.you_and_others
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.model.ChatMember.Companion.isGroup
import com.plcoding.feature.chat.presentation.model.ChatHeaderPm
import com.plcoding.feature.chat.presentation.model.ChatMemberPm

class ChatHeaderPmMapper(
  private val chatMemberPmMapper: ChatMemberPmMapper,
) : Mapper<List<ChatMember>, ChatHeaderPm, ChatHeaderPmMapper.Params> {

  override fun map(from: List<ChatMember>, params: Params): ChatHeaderPm {
    return with(from) {
      ChatHeaderPm(
        avatarsPm = chatMemberPmMapper
          .map(this, Unit)
          .map(ChatMemberPm::avatarPm),
        title = getTitle(this, params),
        description = getDescription(this, params),
      )
    }
  }

  private fun getTitle(from: List<ChatMember>, params: Params): TextProvider {
    return if (from.isGroup) {
      TextProvider.Resource(Res.string.group_chat, listOf())
    } else {
      val member = from.first { it.userId != params.yourId }
      TextProvider.Dynamic(member.username)
    }
  }

  private fun getDescription(from: List<ChatMember>, params: Params): TextProvider? {
    if (!from.isGroup) return null

    val remoteMembers = from.filter { it.userId != params.yourId }
    val formattedRemoteMembers = remoteMembers.joinToString { it.username }
    return TextProvider.Resource(Res.string.you_and_others, listOf(formattedRemoteMembers))
  }

  data class Params(
    val yourId: String,
  )
}
