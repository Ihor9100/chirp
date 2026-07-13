package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.presentation.mapper.toUi

data class ChatMemberUi(
  val id: String,
  val fullName: TextProvider,
  val avatarUi: AvatarUi,
) {
  companion object {
    val mocks get() = ChatMember.mocks.map { it.toUi(isInChat = false) }
  }
}
