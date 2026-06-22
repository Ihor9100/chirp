package com.plcoding.feature.chat.presentation.screen.chats

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.presentation.mapper.ChatDetailsPmMapper
import com.plcoding.feature.chat.presentation.mapper.ChatHeaderPmMapper
import com.plcoding.feature.chat.presentation.mapper.ChatPmMapper
import com.plcoding.feature.chat.presentation.model.ChatEmptyStatePm
import com.plcoding.feature.chat.presentation.screen.chats.ChatsScreenContentPmMapper.From
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

class ChatsScreenContentPmMapper(
  private val chatPmMapper: ChatPmMapper,
  private val chatHeaderPmMapper: ChatHeaderPmMapper,
  private val chatDetailsPmMapper: ChatDetailsPmMapper,
) : Mapper<From, ChatsScreenContentPm, Unit> {

  override fun map(
    from: From,
    params: Unit
  ): ChatsScreenContentPm {
    return with(from) {
      ChatsScreenContentPm(
        chatsEmptyState = getChatEmptyState(
          predicate = from.chats::isEmpty,
          descriptionRes = Res.string.no_messages_subtitle,
        ),
        noChatEmptyState = getChatEmptyState(
          predicate = { chatId == null },
          descriptionRes = Res.string.select_chat_subtitle,
        ),
        chatId = chatId,
        chatsPm = chatPmMapper.mapList(
          chats,
          ChatPmMapper.Params(
            yourId = yourId,
            chatId = chatId,
            lastChatId = chats.lastOrNull()?.id,
          ),
        ),
        chatEmptyState = getChatEmptyState(
          predicate = {
            chatDetails?.chatMessageAndMembers
              ?.map { it.chatMessage }
              .isNullOrEmpty()
          },
          descriptionRes = Res.string.no_messages_subtitle,
        ),
        chatHeaderPm = chatDetails?.run {
          chatHeaderPmMapper.map(
            chatMessageAndMembers.map { it.chatMember },
            ChatHeaderPmMapper.Params(yourId)
          )
        },
        chatDetailsPm = chatDetails?.let {
          chatDetailsPmMapper.map(it, Unit)
        }.orEmpty(),
      )
    }
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
    val chatId: String?,
    val chats: List<Chat>,
    val chatDetails: ChatDetails?,
  )
}