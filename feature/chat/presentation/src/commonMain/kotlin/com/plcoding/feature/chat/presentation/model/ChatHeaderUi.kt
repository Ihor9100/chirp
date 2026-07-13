package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.mapper.toChatHeaderUi

data class ChatHeaderUi(
  val avatarsUi: List<AvatarUi>,
  val title: TextProvider,
  val description: TextProvider?,
) {
  companion object {
    val mock get() = Chat.mocks.first().toChatHeaderUi(yourId = "1")
  }
}
