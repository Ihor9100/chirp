package com.plcoding.feature.chat.presentation.mapper

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.in_chat_member
import com.plcoding.core.designsystem.model.AvatarSizeUi
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.presentation.model.ChatMemberUi
import com.plcoding.feature.chat.presentation.utils.FormatUtils.getInitials

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
