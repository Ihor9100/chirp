package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.mapper.ChatHeaderPmMapper
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import com.plcoding.feature.chat.presentation.mapper.ChatPmMapper

data class ChatPm(
  val id: String,
  val chatHeaderPm: ChatHeaderPm,
  val content: TextProvider?,
  val backgroundColorToken: ColorToken,
  val showVerticalDivider: Boolean,
  val showHorizontalDivider: Boolean,
) {

  companion object {
    val mocks
      get() = ChatPmMapper(ChatHeaderPmMapper(ChatMemberPmMapper())).map(
        from = Chat.mocks,
        params = ChatPmMapper.Params(
          yourId = "1",
          selectedChatId = "1",
          lastChatId = Chat.mocks.last().id,
        ),
      )
  }
}
