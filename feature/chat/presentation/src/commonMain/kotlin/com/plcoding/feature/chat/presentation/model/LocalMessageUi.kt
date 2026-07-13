package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.ChatMessageUi

data class LocalMessageUi(
  val chatMessageUi: ChatMessageUi,
  val chatSendingStatusUi: ChatSendingStatusUi?,
  val showRetryIcon: Boolean,
) : ChatDetailsUi {

  companion object {
    val mock
      get() = LocalMessageUi(
        chatMessageUi = ChatMessageUi.mocks[1],
        chatSendingStatusUi = ChatSendingStatusUi.error,
        showRetryIcon = true,
      )
  }
}
