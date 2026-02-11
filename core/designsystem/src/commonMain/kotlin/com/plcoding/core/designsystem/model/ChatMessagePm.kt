package com.plcoding.core.designsystem.model

import androidx.compose.runtime.Composable

data class ChatMessagePm(
  val anchorPositionPm: AnchorPositionPm,
  val sender: String,
  val date: String,
  val message: String,
  val chatSendingStatusPm: ChatSendingStatusPm?,
) {

  companion object {
    val mocks
      @Composable
      get() = listOf(
        ChatMessagePm(
          anchorPositionPm = AnchorPositionPm.LEFT,
          sender = "Friend",
          date = "Today",
          message = "Hello",
          chatSendingStatusPm = ChatSendingStatusPm.success,
        ),
        ChatMessagePm(
          anchorPositionPm = AnchorPositionPm.RIGHT,
          sender = "You",
          date = "Today",
          message = "Hello",
          chatSendingStatusPm = ChatSendingStatusPm.success,
        ),
      )
  }
}
