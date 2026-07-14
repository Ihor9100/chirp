package com.plcoding.feature.chat.presentation.screen.chats

import chirp.core.designsystem.generated.resources.ic_users
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.ic_log_out
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import chirp.feature.chat.presentation.generated.resources.select_chat_subtitle
import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.presentation.mapper.toChatHeaderUi
import com.plcoding.feature.chat.presentation.mapper.toUi
import com.plcoding.feature.chat.presentation.model.ChatEmptyStateUi
import com.plcoding.feature.chat.presentation.model.ChatHeaderUi
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import com.plcoding.feature.chat.presentation.model.ChatUi
import org.jetbrains.compose.resources.StringResource
import chirp.core.designsystem.generated.resources.Res as CoreRes

data class ChatsUiState(
  val chatsEmptyStateUi: ChatEmptyStateUi?,
  val chatId: String?,
  val chatsUi: List<ChatUi>,
  val chatEmptyStateUi: ChatEmptyStateUi?,
  val chatHeaderUi: ChatHeaderUi?,
  val dropDownItemsUi: List<DropDownItemUi>?,
  val openChatManageEvent: Event<String>?,
  val leaveChatEvent: Event<Unit>?,
  val chatMessagesUi: List<ChatMessageUi>?,
) {
  companion object {
    val mock
      get() = from(
        yourId = "1",
        chats = Chat.mocks,
        chatDetails = null,
        internalState = ChatsScreenViewModel.InternalState(),
      )

    fun from(
      yourId: String?,
      chats: List<Chat>,
      chatDetails: ChatDetails?,
      internalState: ChatsScreenViewModel.InternalState,
    ): ChatsUiState {
      return ChatsUiState(
        chatsEmptyStateUi = getChatEmptyStateUi(
          predicate = chats::isEmpty,
          descriptionRes = Res.string.no_messages_subtitle,
        ),
        chatId = internalState.chatId,
        chatsUi = chats.map {
          it.toUi(
            yourId = yourId,
            chatId = internalState.chatId,
            lastChatId = chats.lastOrNull()?.id,
          )
        },
        chatEmptyStateUi = getChatEmptyStateUi(
          predicate = { internalState.chatId == null },
          descriptionRes = Res.string.select_chat_subtitle,
        ),
        chatHeaderUi = chatDetails?.chat?.toChatHeaderUi(yourId),
        dropDownItemsUi = if (internalState.showChatDetailsDropDown) chatsDropDownItems() else null,
        openChatManageEvent = internalState.openChatManageEvent,
        leaveChatEvent = internalState.leaveChatEvent,
        chatMessagesUi = chatDetails?.chatMessagesAndMembers?.map { it.toUi(yourId) },
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

    private fun getChatEmptyStateUi(
      predicate: () -> Boolean,
      descriptionRes: StringResource,
    ): ChatEmptyStateUi? {
      return if (predicate()) {
        ChatEmptyStateUi(
          titleRes = Res.string.no_messages,
          descriptionRes = descriptionRes,
        )
      } else {
        null
      }
    }
  }
}