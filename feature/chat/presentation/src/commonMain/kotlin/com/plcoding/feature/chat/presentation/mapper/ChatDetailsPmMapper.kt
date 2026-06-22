package com.plcoding.feature.chat.presentation.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.presentation.model.ChatDetailsPm

class ChatDetailsPmMapper : Mapper<ChatDetails, List<ChatDetailsPm>, Unit> {

  // TODO:
  override fun map(
    from: ChatDetails,
    params: Unit
  ): List<ChatDetailsPm> {
    return with(from) {
      ChatDetailsPm.mocks
    }
  }
}
