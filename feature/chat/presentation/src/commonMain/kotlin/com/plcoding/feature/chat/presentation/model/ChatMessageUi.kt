package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.designsystem.model.ChatBubbleUi
import com.plcoding.feature.chat.domain.model.ChatMessage

sealed class ChatMessageUi(open val id: String) {

  data class DateDividerUi(
    override val id: String,
    val date: String,
  ) : ChatMessageUi(id) {

    companion object {
      val mock get() = DateDividerUi("Today", "Today")
    }
  }

  data class LocalMessageUi(
    override val id: String,
    val chatBubbleUi: ChatBubbleUi,
    val chatMessageStatusUi: ChatMessageStatusUi?,
    val showRetryIcon: Boolean,
  ) : ChatMessageUi(id) {

    companion object {
      val mock
        get() = LocalMessageUi(
          id = ChatMessage.mock.first().id,
          chatBubbleUi = ChatBubbleUi.mocks[1],
          chatMessageStatusUi = ChatMessageStatusUi.error,
          showRetryIcon = true,
        )
    }
  }

  data class RemoteMessageUi(
    override val id: String,
    val avatarUi: AvatarUi,
    val chatBubbleUi: ChatBubbleUi,
  ) : ChatMessageUi(id) {

    companion object {
      val mock
        get() = RemoteMessageUi(
          id = ChatMessage.mock.first().id,
          avatarUi = AvatarUi.mocks[0],
          chatBubbleUi = ChatBubbleUi.mocks[0],
        )
    }
  }


  companion object {
    val mocks
      get() = listOf(
        DateDividerUi.mock,
        LocalMessageUi.mock,
        RemoteMessageUi.mock,
      )
  }
}
