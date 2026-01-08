package com.plcoding.feature.chat.presentation.screen.chat.create

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.feature.chat.presentation.model.ChatParticipantPm

data class ChatCreateScreenContent(
  val searchTextFieldState: TextFieldState = TextFieldState(),
  val chatParticipantsPm: List<ChatParticipantPm> = listOf(),
)
