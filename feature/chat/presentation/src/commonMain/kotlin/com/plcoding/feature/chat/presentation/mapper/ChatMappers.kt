package com.plcoding.feature.chat.presentation.mapper

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.you_and_others
import chirp.feature.chat.presentation.generated.resources.your_message
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.model.ChatMember.Companion.isGroup
import com.plcoding.feature.chat.presentation.model.ChatHeaderUi
import com.plcoding.feature.chat.presentation.model.ChatUi

fun Chat.toUi(
  yourId: String?,
  chatId: String?,
  lastChatId: String?,
): ChatUi {
  val isSelected = id == chatId
  return ChatUi(
    id = id,
    chatHeaderUi = toChatHeaderUi(yourId),
    content = getLastMessageContent(this, yourId),
    backgroundColorToken = if (isSelected) ColorToken.Surface else ColorToken.SurfaceLower,
    isSelected = isSelected,
    isLast = id == lastChatId,
  )
}

fun Chat.toChatHeaderUi(yourId: String?): ChatHeaderUi {
  return ChatHeaderUi(
    avatarsUi = members.map { it.toUi(isInChat = false).avatarUi },
    title = getChatTitle(yourId, members),
    description = getChatDescription(yourId, members),
  )
}

private fun getChatTitle(yourId: String?, members: List<ChatMember>): TextProvider =
  if (members.isGroup) {
    TextProvider.Resource(Res.string.group_chat, listOf())
  } else {
    val member = members.firstOrNull { it.userId != yourId } ?: members.first()
    TextProvider.Dynamic(member.username)
  }

private fun getChatDescription(yourId: String?, members: List<ChatMember>): TextProvider? {
  if (!members.isGroup) return null
  val others = members.filter { it.userId != yourId }.joinToString { it.username }
  return TextProvider.Resource(Res.string.you_and_others, listOf(others))
}

private fun getLastMessageContent(chat: Chat, yourId: String?): TextProvider? {
  val lastMessage = chat.lastMessage ?: return null
  val sender = chat.members.firstOrNull { it.userId == lastMessage.senderId }
  return if (sender?.userId == yourId) {
    TextProvider.Resource(Res.string.your_message, listOf(lastMessage.content))
  } else {
    TextProvider.Dynamic("${sender?.username}: ${lastMessage.content}")
  }
}
