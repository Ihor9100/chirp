package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.model.AnchorPositionUi
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.presentation.model.TextProvider

data class ChatBoxUi(
  val anchorPositionUi: AnchorPositionUi,
  val sender: String,
  val date: TextProvider,
  val message: String,
  val colorToken: ColorToken,
) {

  companion object {
    val mocks
      get() = listOf(
        ChatBoxUi(
          anchorPositionUi = AnchorPositionUi.LEFT,
          sender = "Friend",
          date = TextProvider.Dynamic("Today"),
          message = "Hello",
          colorToken = ColorToken.get("1"),
        ),
        ChatBoxUi(
          anchorPositionUi = AnchorPositionUi.RIGHT,
          sender = "You",
          date = TextProvider.Dynamic("Today"),
          message = "Hello",
          colorToken = ColorToken.get("2"),
        ),
      )
  }
}