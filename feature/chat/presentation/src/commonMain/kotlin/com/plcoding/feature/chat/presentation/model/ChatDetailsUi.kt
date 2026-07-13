package com.plcoding.feature.chat.presentation.model

interface ChatDetailsUi {
  companion object {
    val mocks get() = listOf(
      DateDividerUi.mock,
      LocalMessageUi.mock,
      RemoteMessageUi.mock,
    )
  }
}
