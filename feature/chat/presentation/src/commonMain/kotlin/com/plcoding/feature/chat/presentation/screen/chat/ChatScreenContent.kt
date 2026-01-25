package com.plcoding.feature.chat.presentation.screen.chat

import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.model.ChatPm

data class ChatScreenContent(
  val chatId: String? = null,
  val chat: Chat? = null,
  val chatsPm: List<ChatPm> = ChatPm.mocks,
)
