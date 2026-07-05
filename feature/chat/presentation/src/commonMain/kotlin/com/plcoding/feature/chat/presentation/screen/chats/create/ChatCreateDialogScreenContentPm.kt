package com.plcoding.feature.chat.presentation.screen.chats.create

import androidx.compose.foundation.text.input.TextFieldState
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.create_chat
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenContentPm
import org.jetbrains.compose.resources.StringResource

data class ChatCreateDialogScreenContentPm(
  override val titleRes: StringResource = Res.string.create_chat,
  override val searchTextFieldState: TextFieldState = TextFieldState(),
  override val inChatMembersPm: List<ChatMemberPm> = listOf(),
  override val foundChatMemberPm: ChatMemberPm? = null,
  override val foundChatMembersPm: List<ChatMemberPm> = listOf(),
  override val positiveButtonRes: StringResource = Res.string.create_chat,
  val chatCreatedEvent: Event<Unit>? = null,
) : BaseChatDialogScreenContentPm<ChatCreateDialogScreenContentPm> {

  override fun update(
    foundChatMemberPm: ChatMemberPm?,
    foundChatMembersPm: List<ChatMemberPm>,
  ): ChatCreateDialogScreenContentPm {
    return copy(
      foundChatMemberPm = foundChatMemberPm,
      foundChatMembersPm = foundChatMembersPm,
    )
  }
}
