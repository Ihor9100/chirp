package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.AvatarPc
import com.plcoding.core.designsystem.components.ChatMessagePc
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.feature.chat.presentation.model.RemoteMessagePm
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RemoteMessagePc(
  modifier: Modifier,
  remoteMessagePm: RemoteMessagePm,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
    verticalAlignment = Alignment.Bottom,
  ) {
    AvatarPc(
      modifier = Modifier,
      avatarPm = remoteMessagePm.avatarPm,
    )
    ChatMessagePc(
      modifier = Modifier,
      chatMessagePm = remoteMessagePm.chatMessagePm,
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    RemoteMessagePc(
      modifier = Modifier,
      remoteMessagePm = RemoteMessagePm.mock,
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
