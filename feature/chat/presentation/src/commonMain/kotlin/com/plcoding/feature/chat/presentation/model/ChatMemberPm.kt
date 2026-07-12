package com.plcoding.feature.chat.presentation.model

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.in_chat_member
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.designsystem.model.AvatarSizePm
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.ChatMember

data class ChatMemberPm(
  val id: String,
  val fullName: TextProvider,
  val avatarPm: AvatarPm,
) {
  companion object {
    val mocks get() = ChatMember.mocks.map { from(it, isInChat = false) }

    fun from(member: ChatMember, isInChat: Boolean): ChatMemberPm = ChatMemberPm(
      id = member.userId,
      fullName = if (isInChat) {
        TextProvider.Resource(Res.string.in_chat_member, listOf(member.username))
      } else {
        TextProvider.Dynamic(member.username)
      },
      avatarPm = AvatarPm(
        initials = getInitials(member.username),
        imageUrl = member.profilePictureUrl,
        avatarSizePm = AvatarSizePm.MEDIUM,
      ),
    )

    private fun getInitials(fullName: String): String {
      if (fullName.isBlank()) return "?"
      return fullName.split(" ").take(2).joinToString(separator = "") { it.first().uppercase() }
    }
  }
}
