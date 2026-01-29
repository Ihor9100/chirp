package com.plcoding.feature.chat.presentation.screen.chats.create

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.model.ChatMemberPm

data class ChatCreateScreenContent(
  val searchTextFieldState: TextFieldState = TextFieldState(),
  val chatMemberPm: ChatMemberPm? = null,
  val chatMembersPm: List<ChatMemberPm> = listOf(),
  val chatCreatedEvent: Event<Chat>? = null,
)
