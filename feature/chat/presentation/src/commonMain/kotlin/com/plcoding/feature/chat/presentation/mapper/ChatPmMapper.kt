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
) : Mapper<ChatPmMapper.From, ChatPm> {

  override fun map(from: From): ChatPm {
    return with(from) {
      val isSelected = chat.id == from.chatId

      ChatPm(
        id = chat.id,
        chatHeaderPm = chatHeaderPmMapper.map(ChatHeaderPmMapper.From(yourId, chat.members)),
        content = getContent(this),
        backgroundColorToken = if (isSelected) ColorToken.Surface else ColorToken.Background,
        showVerticalDivider = isSelected,
        showHorizontalDivider = lastChatId != null && chat.id != lastChatId,
      )
    }
  }

  private fun getContent(from: From): TextProvider? {
    if (from.chat.lastMessage == null) return null

    val member = from.chat.members.firstOrNull {
      it.userId == from.chat.lastMessage?.senderId
    }

    return if (member?.userId == from.yourId) {
      from.chat.lastMessage?.let {
        TextProvider.Resource(
          Res.string.your_message,
          listOf(it.content),
        )
      }
    } else {
      from.chat.lastMessage?.let {
        TextProvider.Dynamic("${member?.username}: ${it.content}")
      }
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
