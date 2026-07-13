package com.plcoding.feature.chat.presentation.screen.chats.base

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.feature.chat.presentation.model.ChatMemberUi
import org.jetbrains.compose.resources.StringResource

interface BaseChatDialogUiState<T : BaseChatDialogUiState<T>> {
  val titleRes: StringResource
  val searchTextFieldState: TextFieldState
  val inChatMembersUi: List<ChatMemberUi>
  val foundChatMemberUi: ChatMemberUi?
  val foundChatMembersUi: List<ChatMemberUi>
  val positiveButtonRes: StringResource

  fun isEmptyState(): Boolean {
    return inChatMembersUi.isEmpty() && foundChatMembersUi.isEmpty()
  }

  fun update(
    foundChatMemberUi: ChatMemberUi? = this.foundChatMemberUi,
    foundChatMembersUi: List<ChatMemberUi> = this.foundChatMembersUi,
  ): T
}
