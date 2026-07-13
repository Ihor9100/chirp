package com.plcoding.feature.chat.presentation.screen.chats.create

import androidx.compose.foundation.text.input.TextFieldState
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.create_chat
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.presentation.model.ChatMemberUi
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogUiState
import org.jetbrains.compose.resources.StringResource

data class ChatCreateDialogUiState(
  override val titleRes: StringResource = Res.string.create_chat,
  override val searchTextFieldState: TextFieldState = TextFieldState(),
  override val inChatMembersUi: List<ChatMemberUi> = listOf(),
  override val foundChatMemberUi: ChatMemberUi? = null,
  override val foundChatMembersUi: List<ChatMemberUi> = listOf(),
  override val positiveButtonRes: StringResource = Res.string.create_chat,
  val chatCreatedEvent: Event<Unit>? = null,
) : BaseChatDialogUiState<ChatCreateDialogUiState> {

  override fun update(
    foundChatMemberUi: ChatMemberUi?,
    foundChatMembersUi: List<ChatMemberUi>,
  ): ChatCreateDialogUiState {
    return copy(
      foundChatMemberUi = foundChatMemberUi,
      foundChatMembersUi = foundChatMembersUi,
    )
  }
}
