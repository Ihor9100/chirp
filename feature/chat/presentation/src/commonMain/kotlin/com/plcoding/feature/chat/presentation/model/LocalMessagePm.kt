package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.ChatMessagePm

data class LocalMessagePm(
  val chatMessagePm: ChatMessagePm,
  val chatSendingStatusPm: ChatSendingStatusPm?,
  val showRetryIcon: Boolean,
) : ChatDetailsPm {

  companion object {
    val mock
      get() = LocalMessagePm(
        chatMessagePm = ChatMessagePm.mocks[1],
        chatSendingStatusPm = ChatSendingStatusPm.error,
        showRetryIcon = true,
      )
  }
}
