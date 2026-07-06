package com.plcoding.feature.chat.presentation.screen.chats

import com.plcoding.core.designsystem.model.DropDownItemPm
import com.plcoding.core.presentation.event.Event
import com.plcoding.feature.chat.presentation.model.ChatDetailsPm
import com.plcoding.feature.chat.presentation.model.ChatEmptyStatePm
import com.plcoding.feature.chat.presentation.model.ChatHeaderPm
import com.plcoding.feature.chat.presentation.model.ChatPm

data class ChatsScreenContentPm(
  val chatsEmptyState: ChatEmptyStatePm?,
  val chatsPm: List<ChatPm>,
  val chatId: String?,
  val chatEmptyState: ChatEmptyStatePm?,
  val chatHeaderPm: ChatHeaderPm?,
  val dropDownItemsPm: List<DropDownItemPm>?,
  val openChatManageEvent: Event<String>?,
  val leaveChatEvent: Event<Unit>?,
  val chatDetailsPm: List<ChatDetailsPm>?,
) {

  companion object {
    val mock
      get() = ChatsScreenContentPm(
        chatsEmptyState = null,
        chatsPm = ChatPm.mocks,
        chatId = ChatPm.mocks.first().id,
        chatEmptyState = null,
        chatHeaderPm = ChatHeaderPm.mock,
        dropDownItemsPm = DropDownItemPm.mocks,
        openChatManageEvent = null,
        leaveChatEvent = null,
        chatDetailsPm = ChatDetailsPm.mocks,
      )
  }
}
