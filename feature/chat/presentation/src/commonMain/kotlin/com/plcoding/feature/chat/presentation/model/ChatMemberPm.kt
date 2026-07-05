package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper

data class ChatMemberPm(
  val id: String,
  val fullName: TextProvider,
  val avatarPm: AvatarPm,
) {

  companion object {
    val mocks
      get() = ChatMemberPmMapper().mapList(
        ChatMember.mocks.map {
          ChatMemberPmMapper.From(it, false)
        }
      )
  }
}
