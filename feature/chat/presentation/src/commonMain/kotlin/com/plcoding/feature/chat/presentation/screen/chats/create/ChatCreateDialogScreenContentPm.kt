package com.plcoding.feature.chat.presentation.screen.chats.create

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenContentPm

data class ChatCreateDialogScreenContentPm(
  override val searchTextFieldState: TextFieldState = TextFieldState(),
  override val inChatMembersPm: List<ChatMemberPm> = listOf(),
  override val foundChatMemberPm: ChatMemberPm? = null,
  override val foundChatMembersPm: List<ChatMemberPm> = listOf(),
  override val chatCreatedEvent: Event<Unit>? = null,
) : BaseChatDialogScreenContentPm<ChatCreateDialogScreenContentPm> {

  override fun update(
    foundChatMemberPm: ChatMemberPm?,
    foundChatMembersPm: List<ChatMemberPm>,
    chatCreatedEvent: Event<Unit>?
  ): ChatCreateDialogScreenContentPm {
    return copy(
      foundChatMemberPm = foundChatMemberPm,
      foundChatMembersPm = foundChatMembersPm,
      chatCreatedEvent = chatCreatedEvent
    )
  }
}
