package com.plcoding.feature.chat.presentation.screen.chats.base

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import org.jetbrains.compose.resources.StringResource

interface BaseChatDialogScreenContentPm<T : BaseChatDialogScreenContentPm<T>> {
  val titleRes: StringResource
  val searchTextFieldState: TextFieldState
  val inChatMembersPm: List<ChatMemberPm>
  val foundChatMemberPm: ChatMemberPm?
  val foundChatMembersPm: List<ChatMemberPm>
  val positiveButtonRes: StringResource

  fun update(
    foundChatMemberPm: ChatMemberPm? = this.foundChatMemberPm,
    foundChatMembersPm: List<ChatMemberPm> = this.foundChatMembersPm,
  ): T
}
