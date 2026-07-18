package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatMessages(
  modifier: Modifier,
  chatMessagesUi: List<ChatMessageUi>,
  lazyListState: LazyListState,
  onRetryClick: (messageId: String) -> Unit,
) {
  LazyColumn(
    modifier = modifier,
    state = lazyListState,
    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    items(chatMessagesUi) {
      when (it) {
        is ChatMessageUi.DateDividerUi -> DateDivider(
          modifier = Modifier,
          dateDividerPm = it,
        )
        is ChatMessageUi.LocalMessageUi -> LocalMessage(
          modifier = Modifier,
          localMessageUi = it,
          onRetryClick = onRetryClick,
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
    ChatMessages(
      modifier = Modifier,
      chatMessagesUi = ChatMessageUi.mocks,
      lazyListState = rememberLazyListState(),
      onRetryClick = {},
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
