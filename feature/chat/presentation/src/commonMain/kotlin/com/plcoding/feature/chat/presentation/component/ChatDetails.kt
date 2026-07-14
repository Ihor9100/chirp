package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatMessage(
  modifier: Modifier,
  chatMessageUi: List<ChatMessageUi>,
) {
  LazyColumn(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    items(chatMessageUi) {
      when (it) {
        is ChatMessageUi.DateDividerUi -> DateDivider(
          modifier = Modifier,
          dateDividerPm = it,
        )
        is ChatMessageUi.LocalMessageUi -> LocalMessage(
          modifier = Modifier,
          localMessageUi = it,
        )
        is ChatMessageUi.RemoteMessageUi -> RemoteMessage(
          modifier = Modifier,
          remoteMessagePm = it,
        )
      }
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    ChatMessage(
      modifier = Modifier,
      chatMessageUi = ChatMessageUi.mocks,
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
