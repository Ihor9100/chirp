package com.plcoding.feature.chat.presentation.mapper

import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.presentation.model.ChatDetailsPm

class ChatDetailsPmMapper : Mapper<ChatDetails, List<ChatDetailsPm>> {

  // TODO:
  override fun map(from: ChatDetails): List<ChatDetailsPm> {
    return with(from) {
      ChatDetailsPm.mocks
    }
  }
}
