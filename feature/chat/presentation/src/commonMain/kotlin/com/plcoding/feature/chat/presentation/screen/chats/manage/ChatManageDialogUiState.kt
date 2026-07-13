package com.plcoding.feature.chat.presentation.screen.chats.manage

import androidx.compose.foundation.text.input.TextFieldState
import chirp.core.presentation.generated.resources.save
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.presentation.model.ChatMemberUi
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogUiState
import org.jetbrains.compose.resources.StringResource
import chirp.core.presentation.generated.resources.Res as CoreRes

data class ChatManageDialogUiState(
  override val titleRes: StringResource = Res.string.chat_members,
  override val searchTextFieldState: TextFieldState = TextFieldState(),
  override val inChatMembersUi: List<ChatMemberUi> = listOf(),
  override val foundChatMemberUi: ChatMemberUi? = null,
  override val foundChatMembersUi: List<ChatMemberUi> = listOf(),
  override val positiveButtonRes: StringResource = CoreRes.string.save,
  val chatUpdatedEvent: Event<Unit>? = null,
) : BaseChatDialogUiState<ChatManageDialogUiState> {

  override fun update(
    foundChatMemberUi: ChatMemberUi?,
    foundChatMembersUi: List<ChatMemberUi>,
  ): ChatManageDialogUiState {
    return copy(
      foundChatMemberUi = foundChatMemberUi,
      foundChatMembersUi = foundChatMembersUi,
    )
  }
}
