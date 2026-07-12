package com.plcoding.feature.chat.presentation.model

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.your_message
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.Chat

data class ChatPm(
  val id: String,
  val chatHeaderPm: ChatHeaderPm,
  val content: TextProvider?,
  val backgroundColorToken: ColorToken,
  val showVerticalDivider: Boolean,
  val showHorizontalDivider: Boolean,
) {
  companion object {
    val mocks
      get() = Chat.mocks.map {
        from(it, yourId = "1", chatId = "1", lastChatId = Chat.mocks.last().id)
      }

    fun from(
      chat: Chat,
      yourId: String?,
      chatId: String?,
      lastChatId: String?,
    ): ChatPm {
      val isSelected = chat.id == chatId
      return ChatPm(
        id = chat.id,
        chatHeaderPm = ChatHeaderPm.from(chat, yourId),
        content = getContent(chat, yourId),
        backgroundColorToken = if (isSelected) ColorToken.Surface else ColorToken.Background,
        showVerticalDivider = isSelected,
        showHorizontalDivider = lastChatId != null && chat.id != lastChatId,
      )
    }

    private fun getContent(chat: Chat, yourId: String?): TextProvider? {
      val lastMessage = chat.lastMessage ?: return null
      val sender = chat.members.firstOrNull { it.userId == lastMessage.senderId }
      return if (sender?.userId == yourId) {
        TextProvider.Resource(Res.string.your_message, listOf(lastMessage.content))
      } else {
        TextProvider.Dynamic("${sender?.username}: ${lastMessage.content}")
      }
    }
  }
}
