package com.plcoding.feature.chat.presentation.model

import androidx.compose.runtime.Composable
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.designsystem.model.ChatMessagePm

class RemoteMessagePm(
  val avatarPm: AvatarPm,
  val chatMessagePm: ChatMessagePm,
) : ChatDetailsPm {

  companion object {
    val mock
      @Composable
      get() = RemoteMessagePm(
        avatarPm = AvatarPm.mock,
        chatMessagePm = ChatMessagePm.mocks[0]
      )
  }
}
