package com.plcoding.feature.chat.presentation.model

import androidx.compose.runtime.Composable
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.designsystem.model.ChatMessageUi

class RemoteMessageUi(
  val avatarUi: AvatarUi,
  val chatMessageUi: ChatMessageUi,
) : ChatDetailsUi {

  companion object {
    val mock
      get() = RemoteMessageUi(
        avatarUi = AvatarUi.mocks[0],
        chatMessageUi = ChatMessageUi.mocks[0]
      )
  }
}
