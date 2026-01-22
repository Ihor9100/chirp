package com.plcoding.feature.chat.presentation.screen.chat

import com.plcoding.feature.chat.domain.model.Chat

data class ChatScreenContent(
  val chatId: String? = null,
  val chat: Chat? = null,
)
