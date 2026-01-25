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
  val isSelected: Boolean,
) {

  companion object {
    val mocks
      get() = ChatPmMapper(
        ChatMemberPmMapper(),
      ).map(
        Chat.mocks,
        ChatPmMapper.Params(yourId = "1", "1"),
      )
  }
}
