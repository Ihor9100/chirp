package com.plcoding.feature.chat.presentation.screen.chats.manage

import androidx.compose.foundation.text.input.TextFieldState
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.core.presentation.generated.resources.Res as CoreRes
import chirp.feature.chat.presentation.generated.resources.create_chat
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenContentPm
import org.jetbrains.compose.resources.StringResource

data class ChatManageDialogScreenContentPm(
  override val titleRes: StringResource = Res.string.chat_members,
  override val searchTextFieldState: TextFieldState = TextFieldState(),
  override val inChatMembersPm: List<ChatMemberPm> = listOf(),
  override val foundChatMemberPm: ChatMemberPm? = null,
  override val foundChatMembersPm: List<ChatMemberPm> = listOf(),
  override val positiveButtonRes: StringResource = CoreRes.string.save,
  override val chatCreatedEvent: Event<Unit>? = null,
) : BaseChatDialogScreenContentPm<ChatManageDialogScreenContentPm> {

  override fun update(
    foundChatMemberPm: ChatMemberPm?,
    foundChatMembersPm: List<ChatMemberPm>,
    chatCreatedEvent: Event<Unit>?
  ): ChatManageDialogScreenContentPm {
    return copy(
      foundChatMemberPm = foundChatMemberPm,
      foundChatMembersPm = foundChatMembersPm,
      chatCreatedEvent = chatCreatedEvent
    )
  }
}
