package com.plcoding.feature.chat.presentation.screen.chats

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.mapper.ChatPmMapper

class ChatsScreenContentPmMapper(
  private val chatPmMapper: ChatPmMapper,
) : Mapper<List<Chat>, ChatsScreenContentPm, ChatsScreenContentPmMapper.Params> {

  override fun map(
    from: List<Chat>,
    params: Params
  ): ChatsScreenContentPm {
    return with(from) {
      ChatsScreenContentPm(
        chatsPm = chatPmMapper.mapList(
          this,
          ChatPmMapper.Params(
            yourId = params.yourId,
            selectedChatId = params.selectedChatId,
            lastChatId = lastOrNull()?.id,
          ),
        )
      )
    }
  }

  data class Params(
    val yourId: String?,
    val selectedChatId: String?,
  )
}