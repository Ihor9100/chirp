package com.plcoding.feature.chat.presentation.screen.chats

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.presentation.mapper.ChatDetailsPmMapper
import com.plcoding.feature.chat.presentation.mapper.ChatHeaderPmMapper
import com.plcoding.feature.chat.presentation.mapper.ChatPmMapper
import com.plcoding.feature.chat.presentation.screen.chats.ChatsScreenContentPmMapper.From

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
        chatsPm = chatPmMapper.mapList(
          chats,
          ChatPmMapper.Params(
            yourId = yourId,
            chatId = chatId,
            lastChatId = chats.lastOrNull()?.id,
          ),
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

  data class From(
    val yourId: String?,
    val chatId: String?,
    val chats: List<Chat>,
    val chatDetails: ChatDetails?,
  )
}