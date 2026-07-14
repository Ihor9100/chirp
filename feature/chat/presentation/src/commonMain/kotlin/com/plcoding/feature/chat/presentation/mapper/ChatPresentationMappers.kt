package com.plcoding.feature.chat.presentation.mapper

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.in_chat_member
import chirp.feature.chat.presentation.generated.resources.you_and_others
import chirp.feature.chat.presentation.generated.resources.your_message
import com.plcoding.core.designsystem.model.AvatarSizeUi
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.model.ChatMember.Companion.isGroup
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import com.plcoding.feature.chat.presentation.model.ChatHeaderUi
import com.plcoding.feature.chat.presentation.model.ChatMemberUi
import com.plcoding.feature.chat.presentation.model.ChatUi

fun ChatMember.toUi(isInChat: Boolean): ChatMemberUi = ChatMemberUi(
  id = userId,
  fullName = if (isInChat) {
    TextProvider.Resource(Res.string.in_chat_member, listOf(username))
  } else {
    TextProvider.Dynamic(username)
  },
  avatarUi = AvatarUi(
    initials = getInitials(username),
    imageUrl = profilePictureUrl,
    avatarSizeUi = AvatarSizeUi.MEDIUM,
  ),
)

fun Chat.toChatHeaderUi(yourId: String?): ChatHeaderUi = ChatHeaderUi(
  avatarsUi = members.map { it.toUi(isInChat = false).avatarUi },
  title = getChatTitle(yourId, members),
  description = getChatDescription(yourId, members),
)

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
    backgroundColorToken = if (isSelected) ColorToken.Surface else ColorToken.Background,
    showVerticalDivider = isSelected,
    showHorizontalDivider = lastChatId != null && id != lastChatId,
  )
}

fun ChatDetails.toUi(): List<ChatMessageUi> = listOf(
  DateDividerUi.mock,
  LocalMessageUi.mock,
  RemoteMessageUi.mock,
)

private fun getInitials(fullName: String): String {
  if (fullName.isBlank()) return "?"
  return fullName.split(" ").take(2).joinToString(separator = "") { it.first().uppercase() }
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
