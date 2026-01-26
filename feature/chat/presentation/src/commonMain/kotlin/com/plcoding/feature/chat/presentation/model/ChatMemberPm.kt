package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper

data class ChatMemberPm(
  val id: String,
  val fullName: String,
  val avatarPm: AvatarPm,
) {

  companion object {
    val mocks get() = ChatMemberPmMapper().map(ChatMember.mocks, Unit)
  }
}
