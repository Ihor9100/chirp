package com.plcoding.feature.chat.presentation.screen.chats.list

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.mapper.toUi
import com.plcoding.feature.chat.presentation.model.ChatEmptyStateUi
import com.plcoding.feature.chat.presentation.model.ChatUi

data class ChatsListScreenUiState(
  val avatarUi: AvatarUi? = null,
  val showDropDownMenu: Boolean = false,
  val logoutEvent: Event<Unit>? = null,
  val chatEmptyStateUi: ChatEmptyStateUi? = null,
  val chatsUi: List<ChatUi> = listOf(),
) {

  companion object {
    val mock
      get() = from(
        yourId = "1",
        chatId = "1",
        chats = Chat.mocks,
      )

    fun from(
      yourId: String?,
      chatId: String?,
      chats: List<Chat>,
    ): ChatsListScreenUiState {
      return ChatsListScreenUiState(
        chatEmptyStateUi = if (chats.isEmpty()) {
          ChatEmptyStateUi(
            titleRes = Res.string.no_messages,
            descriptionRes = Res.string.no_messages_subtitle,
          )
        } else {
          null
        },
        chatsUi = chats.map {
          it.toUi(
            yourId = yourId,
            chatId = chatId,
            lastChatId = chats.lastOrNull()?.id,
          )
        },
      )
    }
  }
}