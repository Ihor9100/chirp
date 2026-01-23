package com.plcoding.feature.chat.presentation.model

import com.plcoding.core.designsystem.components.AvatarPm
import com.plcoding.core.presentation.utils.TextProvider

data class ChatPm(
  val id: String,
  val avatarsPm: List<AvatarPm>,
  val title: TextProvider,
  val description: TextProvider?,
  val content: TextProvider?,
) {

  companion object {
    val mock
      get() = ChatPm(
        id = "1",
        avatarsPm = AvatarPm.mocks,
        title = TextProvider.Dynamic("Test Title"),
        description = TextProvider.Dynamic("Test Description"),
        content = TextProvider.Dynamic("TestTestTestTestTestTestTestTestTestTestTestTest"),
      )
  }
}
