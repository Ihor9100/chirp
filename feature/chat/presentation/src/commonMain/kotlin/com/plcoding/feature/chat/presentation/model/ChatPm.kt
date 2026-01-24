package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import com.plcoding.feature.chat.presentation.mapper.ChatPmMapper

data class ChatPm(
  val id: String,
  val avatarsPm: List<AvatarPm>,
  val title: TextProvider,
  val description: TextProvider?,
  val content: TextProvider?,
) {

  companion object {
    val mock
      get() = ChatPmMapper(
        ChatMemberPmMapper(),
      ).map(
        Chat.mock,
        ChatPmMapper.Params(yourId = "1"),
      )
  }
}
