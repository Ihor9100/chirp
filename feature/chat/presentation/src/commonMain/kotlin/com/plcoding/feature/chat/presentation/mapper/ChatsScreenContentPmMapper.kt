package com.plcoding.feature.chat.presentation.mapper

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.your_message
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.core.presentation.utils.TextProvider
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.presentation.mapper.ChatsScreenContentPmMapper.Params
import com.plcoding.feature.chat.presentation.model.ChatPm
import com.plcoding.feature.chat.presentation.screen.chats.ChatsScreenContentPm

class ChatsScreenContentPmMapper(
  private val chatPmMapper: ChatPmMapper,
) : Mapper<List<Chat>, ChatsScreenContentPm, Params> {

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
