package com.plcoding.feature.chat.presentation.screen.chat.create

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.feature.chat.presentation.model.ChatMemberPm

data class ChatCreateScreenContent(
  val searchTextFieldState: TextFieldState = TextFieldState(),
  val chatMemberPm: ChatMemberPm? = null,
  val chatMembersPm: List<ChatMemberPm> = listOf(),
)
