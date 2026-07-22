package com.plcoding.feature.chat.presentation.screen.chats.details

import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.designsystem.model.MultilineTextFieldUi
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.presentation.model.ChatEmptyStateUi
import com.plcoding.feature.chat.presentation.model.ChatHeaderUi
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import org.jetbrains.compose.resources.StringResource

data class ChatDetailsScreenUiState(
  val chatEmptyStateUi: ChatEmptyStateUi?,
  val chatHeaderUi: ChatHeaderUi?,
  val dropDownItemsUi: List<DropDownItemUi>?,
  val openChatManageEvent: Event<String>?,
  val leaveChatEvent: Event<Unit>?,
  val chatMessagesUi: List<ChatMessageUi>?,
  val isPageLoading: Boolean,
  val pageLoadingError: StringResource?,
  val isLastPage: Boolean,
  val longPressedMessageId: String?,
  val showScrollToStartButton: Boolean,
  val scrollToStart: Event<Unit>?,
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
        isPageLoading = false,
        pageLoadingError = null,
        isLastPage = false,
        longPressedMessageId = null,
        showScrollToStartButton = false,
        scrollToStart = null,
        multilineTextFieldUi = MultilineTextFieldUi.mock,
      )
  }
}