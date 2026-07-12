package com.plcoding.feature.chat.presentation.screen.chats

import chirp.core.designsystem.generated.resources.ic_users
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.ic_log_out
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import chirp.feature.chat.presentation.generated.resources.select_chat_subtitle
import com.plcoding.core.designsystem.model.DropDownItemPm
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.presentation.model.ChatDetailsPm
import com.plcoding.feature.chat.presentation.model.ChatEmptyStatePm
import com.plcoding.feature.chat.presentation.model.ChatHeaderPm
import com.plcoding.feature.chat.presentation.model.ChatPm
import org.jetbrains.compose.resources.StringResource
import chirp.core.designsystem.generated.resources.Res as CoreRes

data class ChatsScreenContentPm(
  val chatsEmptyState: ChatEmptyStatePm?,
  val chatId: String?,
  val chatsPm: List<ChatPm>,
  val chatEmptyState: ChatEmptyStatePm?,
  val chatHeaderPm: ChatHeaderPm?,
  val dropDownItemsPm: List<DropDownItemPm>?,
  val openChatManageEvent: Event<String>?,
  val leaveChatEvent: Event<Unit>?,
  val chatDetailsPm: List<ChatDetailsPm>,
) {
  companion object {
    val mock
      get() = from(
        yourId = "1",
        chats = Chat.mocks,
        chatDetails = null,
        internalState = ChatsScreenViewModel.InternalState(
          chatId = null,
          showChatDetailsDropDown = false,
          openChatManageEvent = null,
          leaveChatEvent = null,
        ),
      )

    fun from(
      yourId: String?,
      chats: List<Chat>,
      chatDetails: ChatDetails?,
      internalState: ChatsScreenViewModel.InternalState,
    ): ChatsScreenContentPm {
      return ChatsScreenContentPm(
        chatsEmptyState = getChatEmptyState(
          predicate = chats::isEmpty,
          descriptionRes = Res.string.no_messages_subtitle,
        ),
        chatId = internalState.chatId,
        chatsPm = getChatsPm(yourId, chats, internalState),
        chatEmptyState = getChatEmptyState(
          predicate = { internalState.chatId == null },
          descriptionRes = Res.string.select_chat_subtitle,
        ),
        chatHeaderPm = chatDetails?.chat?.let { ChatHeaderPm.from(it, yourId) },
        dropDownItemsPm = if (internalState.showChatDetailsDropDown) getDropDownItemsPm() else null,
        openChatManageEvent = internalState.openChatManageEvent,
        leaveChatEvent = internalState.leaveChatEvent,
        chatDetailsPm = chatDetails?.let { ChatDetailsPm.from(it) }.orEmpty(),
      )
    }

    private fun getChatsPm(
      yourId: String?,
      chats: List<Chat>,
      internalState: ChatsScreenViewModel.InternalState
    ): List<ChatPm> {
      return chats.map {
        ChatPm.from(
          chat = it,
          yourId = yourId,
          chatId = internalState.chatId,
          lastChatId = chats.lastOrNull()?.id,
        )
      }
    }

    private fun getDropDownItemsPm(): List<DropDownItemPm> {
      return listOf(
        DropDownItemPm(
          leadingIconRes = CoreRes.drawable.ic_users,
          titleRes = Res.string.chat_members,
          colorToken = ColorToken.TextSecondary,
        ),
        DropDownItemPm(
          leadingIconRes = Res.drawable.ic_log_out,
          titleRes = Res.string.log_out,
          colorToken = ColorToken.TextDestructive,
        ),
      )
    }

    private fun getChatEmptyState(
      predicate: () -> Boolean,
      descriptionRes: StringResource,
    ): ChatEmptyStatePm? {
      return if (predicate()) {
        ChatEmptyStatePm(
          titleRes = Res.string.no_messages,
          descriptionRes = descriptionRes,
        )
      } else {
        null
      }
    }
  }
}
