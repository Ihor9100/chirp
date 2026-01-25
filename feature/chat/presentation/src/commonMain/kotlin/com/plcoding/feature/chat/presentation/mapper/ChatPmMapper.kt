package com.plcoding.feature.chat.presentation.mapper

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.you_and_others
import chirp.feature.chat.presentation.generated.resources.your_message
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import com.plcoding.feature.chat.presentation.model.ChatPm

class ChatPmMapper(
  private val chatMemberPmMapper: ChatMemberPmMapper,
) : Mapper<Chat, ChatPm, ChatPmMapper.Params> {

  override fun map(from: Chat, params: Params): ChatPm {
    return with(from) {
      ChatPm(
        id = id,
        avatarsPm = chatMemberPmMapper
          .map(members, Unit)
          .map(ChatMemberPm::avatarPm),
        title = getTitle(this, params),
        description = getDescription(from, params),
        content = getContent(from, params),
        isSelected = id == params.selectedChatId,
      )
    }
  }

  private fun getTitle(from: Chat, params: Params): TextProvider {
    return if (from.isGroup) {
      TextProvider.Resource(Res.string.group_chat, listOf())
    } else {
      val member = from.members.first { it.userId != params.yourId }
      TextProvider.Dynamic(member.username)
    }
  }

  private fun getDescription(from: Chat, params: Params): TextProvider? {
    if (!from.isGroup) return null

    val members = from.members.filter { it.userId != params.yourId }
    val others = members.joinToString { it.username }
    return TextProvider.Resource(Res.string.you_and_others, listOf(others))
  }

  private fun getContent(
    from: Chat,
    params: Params
  ): TextProvider? {
    if (from.lastMessage == null) return null

    val member = from.members.firstOrNull { it.userId == from.lastMessage?.senderId }

    return if (member?.userId == params.yourId) {
      from.lastMessage?.let { TextProvider.Resource(Res.string.your_message, listOf(it.content)) }
    } else {
      from.lastMessage?.let { TextProvider.Dynamic("${member?.username}: ${it.content}") }
    }
  }

  data class Params(
    val yourId: String,
    val selectedChatId: String,
  )
}
