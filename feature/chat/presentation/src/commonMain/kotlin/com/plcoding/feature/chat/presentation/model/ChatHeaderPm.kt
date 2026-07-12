package com.plcoding.feature.chat.presentation.model

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.you_and_others
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.model.ChatMember.Companion.isGroup

data class ChatHeaderPm(
  val avatarsPm: List<AvatarPm>,
  val title: TextProvider,
  val description: TextProvider?,
) {

  companion object {
    val mock get() = from(Chat.mocks.first(), yourId = "1")

    fun from(chat: Chat, yourId: String?): ChatHeaderPm = ChatHeaderPm(
      avatarsPm = chat.members.map { ChatMemberPm.from(it, isInChat = false).avatarPm },
      title = getTitle(yourId, chat.members),
      description = getDescription(yourId, chat.members),
    )

    private fun getTitle(yourId: String?, members: List<ChatMember>): TextProvider =
      if (members.isGroup) {
        TextProvider.Resource(Res.string.group_chat, listOf())
      } else {
        val member = members.firstOrNull { it.userId != yourId } ?: members.first()
        TextProvider.Dynamic(member.username)
      }

    private fun getDescription(yourId: String?, members: List<ChatMember>): TextProvider? {
      if (!members.isGroup) return null
      val others = members.filter { it.userId != yourId }.joinToString { it.username }
      return TextProvider.Resource(Res.string.you_and_others, listOf(others))
    }
  }
}
