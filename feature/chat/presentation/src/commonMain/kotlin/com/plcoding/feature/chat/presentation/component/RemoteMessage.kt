package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.Avatar
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RemoteMessage(
  modifier: Modifier,
  remoteMessagePm: ChatMessageUi.RemoteMessageUi,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
    verticalAlignment = Alignment.Bottom,
  ) {
    Avatar(
      modifier = Modifier,
      avatarUi = remoteMessagePm.avatarUi,
    )
    ChatBox(
      modifier = Modifier,
      chatBoxUi = remoteMessagePm.chatBoxUi,
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    RemoteMessage(
      modifier = Modifier,
      remoteMessagePm = ChatMessageUi.RemoteMessageUi.mock,
    )
  }
}

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkTheme = false
  )
}

@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkTheme = true
  )
}
