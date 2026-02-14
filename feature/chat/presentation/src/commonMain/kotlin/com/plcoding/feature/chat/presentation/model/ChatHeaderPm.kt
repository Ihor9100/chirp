package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.presentation.mapper.ChatHeaderPmMapper
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper

data class ChatHeaderPm(
  val avatarsPm: List<AvatarPm>,
  val title: TextProvider,
  val description: TextProvider?,
) {

  companion object {
    val mock
      get() = ChatHeaderPmMapper(ChatMemberPmMapper()).map(
        from = ChatMember.mocks,
        params = ChatHeaderPmMapper.Params("1"),
      )
  }
}
