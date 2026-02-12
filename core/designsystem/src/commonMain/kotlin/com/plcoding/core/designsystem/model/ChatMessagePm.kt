package com.plcoding.core.designsystem.model

data class ChatMessagePm(
  val anchorPositionPm: AnchorPositionPm,
  val sender: String,
  val date: String,
  val message: String,
) {

  companion object {
    val mocks
      get() = listOf(
        ChatMessagePm(
          anchorPositionPm = AnchorPositionPm.LEFT,
          sender = "Friend",
          date = "Today",
          message = "Hello",
        ),
        ChatMessagePm(
          anchorPositionPm = AnchorPositionPm.RIGHT,
          sender = "You",
          date = "Today",
          message = "Hello",
        ),
      )
  }
}
