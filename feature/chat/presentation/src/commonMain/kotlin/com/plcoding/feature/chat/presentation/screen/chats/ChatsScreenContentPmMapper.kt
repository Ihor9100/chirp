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
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.presentation.mapper.ChatDetailsPmMapper
import com.plcoding.feature.chat.presentation.mapper.ChatHeaderPmMapper
import com.plcoding.feature.chat.presentation.mapper.ChatPmMapper
import com.plcoding.feature.chat.presentation.model.ChatEmptyStatePm
import com.plcoding.feature.chat.presentation.model.ChatPm
import com.plcoding.feature.chat.presentation.screen.chats.ChatsScreenContentPmMapper.From
import org.jetbrains.compose.resources.StringResource
import chirp.core.designsystem.generated.resources.Res as CoreRes

class ChatsScreenContentPmMapper(
  private val chatPmMapper: ChatPmMapper,
  private val chatHeaderPmMapper: ChatHeaderPmMapper,
  private val chatDetailsPmMapper: ChatDetailsPmMapper,
) : Mapper<From, ChatsScreenContentPm> {

  override fun map(from: From): ChatsScreenContentPm {
    return with(from) {
      ChatsScreenContentPm(
        chatsEmptyState = getChatEmptyState(
          predicate = chats::isEmpty,
          descriptionRes = Res.string.no_messages_subtitle,
        ),
        chatId = internalState.chatId,
        chatsPm = getChatsPm(from),
        chatEmptyState = getChatEmptyState(
          predicate = { internalState.chatId == null },
          descriptionRes = Res.string.select_chat_subtitle,
        ),
        chatHeaderPm = chatDetails?.run {
          chatHeaderPmMapper.map(ChatHeaderPmMapper.From(yourId, chat.members))
        },
        dropDownItemsPm = if (internalState.showChatDetailsDropDown) getDropDownItemsPm() else null,
        openChatManageEvent = internalState.openChatManageEvent,
        leaveChatEvent = internalState.leaveChatEvent,
        chatDetailsPm = chatDetails?.let(chatDetailsPmMapper::map).orEmpty(),
      )
    }
  }

  private fun getChatsPm(from: From): List<ChatPm> {
    return from.chats.map {
      chatPmMapper.map(
        ChatPmMapper.From(
          chat = it,
          yourId = from.yourId,
          chatId = from.internalState.chatId,
          lastChatId = from.chats.lastOrNull()?.id,
          showDropDown = from.internalState.showChatDetailsDropDown,
        )
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

  data class From(
    val yourId: String?,
    val chats: List<Chat>,
    val chatDetails: ChatDetails?,
    val internalState: ChatsScreenViewModel.InternalState,
  )
}