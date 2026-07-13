package com.plcoding.core.designsystem.model

import com.plcoding.core.designsystem.style.ColorToken

data class ChatMessageUi(
  val anchorPositionUi: AnchorPositionUi,
  val sender: String,
  val date: String,
  val message: String,
  val colorToken: ColorToken,
) {

  companion object {
    val mocks
      get() = listOf(
        ChatMessageUi(
          anchorPositionUi = AnchorPositionUi.LEFT,
          sender = "Friend",
          date = "Today",
          message = "Hello",
          colorToken = getColorToken("1")
        ),
        ChatMessageUi(
          anchorPositionUi = AnchorPositionUi.RIGHT,
          sender = "You",
          date = "Today",
          message = "Hello",
          colorToken = getColorToken("2")
        ),
      )

    // TODO: extract to mapper 
    fun getColorToken(id: String): ColorToken {
      val colorTokens = listOf(
        ColorToken.CakeViolet,
        ColorToken.CakeGreen,
        ColorToken.CakePink,
        ColorToken.CakeOrange,
        ColorToken.CakeBlue,
        ColorToken.CakeYellow,
        ColorToken.CakePurple,
        ColorToken.CakeRed,
        ColorToken.CakeMint,
      )
      val index = id.hashCode().toUInt() % colorTokens.size.toUInt()
      return colorTokens[index.toInt()]
    }
  }
}
