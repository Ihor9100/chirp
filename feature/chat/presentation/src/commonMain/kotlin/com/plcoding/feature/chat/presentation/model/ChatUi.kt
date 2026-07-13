package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.mapper.toUi

data class ChatUi(
  val id: String,
  val chatHeaderUi: ChatHeaderUi,
  val content: TextProvider?,
  val backgroundColorToken: ColorToken,
  val showVerticalDivider: Boolean,
  val showHorizontalDivider: Boolean,
) {
  companion object {
    val mocks
      get() = Chat.mocks.map {
        it.toUi(
          yourId = "1",
          chatId = "1",
          lastChatId = Chat.mocks.last().id,
        )
      }
  }
}
