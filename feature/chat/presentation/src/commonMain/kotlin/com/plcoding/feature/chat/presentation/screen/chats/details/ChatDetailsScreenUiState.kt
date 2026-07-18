package com.plcoding.feature.chat.presentation.screen.chats.details

import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.designsystem.model.MultilineTextFieldUi
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.presentation.model.ChatEmptyStateUi
import com.plcoding.feature.chat.presentation.model.ChatHeaderUi
import com.plcoding.feature.chat.presentation.model.ChatMessageUi

data class ChatDetailsScreenUiState(
  val chatEmptyStateUi: ChatEmptyStateUi?,
  val chatHeaderUi: ChatHeaderUi?,
  val dropDownItemsUi: List<DropDownItemUi>?,
  val openChatManageEvent: Event<String>?,
  val leaveChatEvent: Event<Unit>?,
  val chatMessagesUi: List<ChatMessageUi>?,
  val longPressedMessageId: String?,
  val scrollToBottom: Event<Unit>?,
  val multilineTextFieldUi: MultilineTextFieldUi,
) {

  companion object {
    val mock
      get() = ChatDetailsScreenUiState(
        chatEmptyStateUi = null,
        chatHeaderUi = null,
        dropDownItemsUi = null,
        openChatManageEvent = null,
        leaveChatEvent = null,
        chatMessagesUi = null,
        longPressedMessageId = null,
        scrollToBottom = null,
        multilineTextFieldUi = MultilineTextFieldUi.mock,
      )
  }
}