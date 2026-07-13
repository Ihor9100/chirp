package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.Avatar
import com.plcoding.core.designsystem.components.ChatMessage
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.feature.chat.presentation.model.RemoteMessageUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RemoteMessage(
  modifier: Modifier,
  remoteMessagePm: RemoteMessageUi,
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
    ChatMessage(
      modifier = Modifier,
      chatMessageUi = remoteMessagePm.chatMessageUi,
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
      remoteMessagePm = RemoteMessageUi.mock,
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
