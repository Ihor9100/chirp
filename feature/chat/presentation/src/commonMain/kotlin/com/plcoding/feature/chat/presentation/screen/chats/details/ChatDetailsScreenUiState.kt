package com.plcoding.feature.chat.presentation.screen.chats.details

import chirp.core.designsystem.generated.resources.ic_users
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.ic_log_out
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.select_chat_subtitle
import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.presentation.mapper.toChatHeaderUi
import com.plcoding.feature.chat.presentation.mapper.toUi
import com.plcoding.feature.chat.presentation.model.ChatEmptyStateUi
import com.plcoding.feature.chat.presentation.model.ChatHeaderUi
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import chirp.core.designsystem.generated.resources.Res as CoreRes

data class ChatDetailsScreenUiState(
  val chatEmptyStateUi: ChatEmptyStateUi?,
  val chatHeaderUi: ChatHeaderUi?,
  val dropDownItemsUi: List<DropDownItemUi>?,
  val openChatManageEvent: Event<String>?,
  val leaveChatEvent: Event<Unit>?,
  val chatMessagesUi: List<ChatMessageUi>?,
  val scrollToBottom: Event<Unit>?,
) {
  companion object {
    val mock
      get() = from(
        yourId = "1",
        chatDetails = null,
        internalState = ChatDetailsScreenViewModel.InternalState(),
      )

    fun from(
      yourId: String?,
      chatDetails: ChatDetails?,
      internalState: ChatDetailsScreenViewModel.InternalState,
    ): ChatDetailsScreenUiState {
      return ChatDetailsScreenUiState(
        chatEmptyStateUi = if (internalState.chatId == null) {
          ChatEmptyStateUi(
            titleRes = Res.string.no_messages,
            descriptionRes = Res.string.select_chat_subtitle,
          )
        } else {
          null
        },
        chatHeaderUi = chatDetails?.chat?.toChatHeaderUi(yourId),
        dropDownItemsUi = if (internalState.showChatDetailsDropDown) chatsDropDownItems() else null,
        openChatManageEvent = internalState.openChatManageEvent,
        leaveChatEvent = null,
        chatMessagesUi = chatDetails?.chatMessagesAndMembers?.map { it.toUi(yourId) },
        scrollToBottom = null,
      )
    }

    private fun chatsDropDownItems(): List<DropDownItemUi> {
      return listOf(
        DropDownItemUi(
          leadingIconRes = CoreRes.drawable.ic_users,
          titleRes = Res.string.chat_members,
          colorToken = ColorToken.TextSecondary,
        ),
        DropDownItemUi(
          leadingIconRes = Res.drawable.ic_log_out,
          titleRes = Res.string.log_out,
          colorToken = ColorToken.TextDestructive,
        ),
      )
    }
  }
}