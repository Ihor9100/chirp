package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.AnchorPositionUi
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.presentation.model.TextProvider

data class ChatBubbleUi(
  val anchorPositionUi: AnchorPositionUi,
  val sender: String,
  val date: TextProvider,
  val message: String,
  val colorToken: ColorToken,
) {

  companion object {
    val mocks
      get() = listOf(
        ChatBubbleUi(
          anchorPositionUi = AnchorPositionUi.LEFT,
          sender = "Friend",
          date = TextProvider.Dynamic("Today"),
          message = "Hello",
          colorToken = ColorToken.get("1"),
        ),
        ChatBubbleUi(
          anchorPositionUi = AnchorPositionUi.RIGHT,
          sender = "You",
          date = TextProvider.Dynamic("Today"),
          message = "Hello",
          colorToken = ColorToken.get("2"),
        ),
      )
  }
}