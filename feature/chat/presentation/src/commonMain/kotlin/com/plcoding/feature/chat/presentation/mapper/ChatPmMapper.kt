package com.plcoding.feature.chat.presentation.mapper

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.your_message
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.model.ChatPm

class ChatPmMapper(
  private val chatHeaderPmMapper: ChatHeaderPmMapper,
) : Mapper<ChatPmMapper.From, ChatPm, Unit> {

  override fun map(from: From, params: Unit): ChatPm {
    return with(from) {
      val isSelected = chat.id == from.chatId

      ChatPm(
        id = chat.id,
        chatHeaderPm = chatHeaderPmMapper.map(
          from = ChatHeaderPmMapper.From(
            yourId = yourId,
            showDropDown = showDropDown,
            chatMembers = chat.members,
          ),
          params = ChatHeaderPmMapper.Params(params.yourId),
        ),
        content = getContent(this, params),
        backgroundColorToken = if (isSelected) ColorToken.Surface else ColorToken.Background,
        showVerticalDivider = isSelected,
        showHorizontalDivider = params.lastChatId != null && id != params.lastChatId,
      )
    }
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

  data class From(
    val chat: Chat,
    val yourId: String?,
    val chatId: String?,
    val lastChatId: String?,
    val showDropDown: Boolean,
  )
}
