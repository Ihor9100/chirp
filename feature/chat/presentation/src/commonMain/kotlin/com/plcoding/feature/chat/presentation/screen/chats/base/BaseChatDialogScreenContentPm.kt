package com.plcoding.feature.chat.presentation.screen.chats.base

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.presentation.model.ChatMemberPm

interface BaseChatDialogScreenContentPm<T : BaseChatDialogScreenContentPm<T>> {
  val searchTextFieldState: TextFieldState
  val inChatMembersPm: List<ChatMemberPm>
  val foundChatMemberPm: ChatMemberPm?
  val foundChatMembersPm: List<ChatMemberPm>
  val chatCreatedEvent: Event<Unit>?

  fun update(
    foundChatMemberPm: ChatMemberPm? = this.foundChatMemberPm,
    foundChatMembersPm: List<ChatMemberPm> = this.foundChatMembersPm,
    chatCreatedEvent: Event<Unit>? = this.chatCreatedEvent,
  ): T
}
