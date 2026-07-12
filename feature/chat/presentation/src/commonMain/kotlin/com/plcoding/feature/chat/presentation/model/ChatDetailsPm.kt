package com.plcoding.feature.chat.presentation.model

import com.plcoding.feature.chat.domain.model.ChatDetails

interface ChatDetailsPm {
  companion object {
    fun from(chatDetails: ChatDetails): List<ChatDetailsPm> = mocks

    val mocks
      get() = listOf(
        DateDividerPm.mock,
        LocalMessagePm.mock,
        RemoteMessagePm.mock,
      )
  }
}
