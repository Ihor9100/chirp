package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import chirp.core.presentation.generated.resources.retry
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.components.button.ButtonStyle
import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import chirp.core.presentation.generated.resources.Res as CoreRes

@Composable
fun ChatMessages(
  modifier: Modifier,
  chatMessagesUi: List<ChatMessageUi>,
  lazyListState: LazyListState,
  longPressedMessageId: String?,
  isPageLoading: Boolean,
  pageLoadingError: StringResource?,
  onLongClick: (messageId: String) -> Unit,
  onMenuItemClick: (DropDownItemUi) -> Unit,
  onMenuDismiss: () -> Unit,
  onMessageRetryClick: (messageId: String) -> Unit,
  onPageRetryClick: () -> Unit,
) {
  LazyColumn(
    modifier = modifier,
    state = lazyListState,
    contentPadding = PaddingValues(vertical = 16.dp),
    reverseLayout = true,
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
          longPressedMessageId = longPressedMessageId,
          onLongClick = onLongClick,
          onMenuClick = onMenuItemClick,
          onMenuDismiss = onMenuDismiss,
          onRetry = onMessageRetryClick,
        )
        is ChatMessageUi.RemoteMessageUi -> RemoteMessage(
          modifier = Modifier,
          remoteMessagePm = it,
        )
      }
    }
    when {
      isPageLoading -> item {
        Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.Center,
        ) {
          CircularProgressIndicator()
        }
      }
      pageLoadingError != null -> item {
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(8.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Button(
            text = stringResource(CoreRes.string.retry),
            style = ButtonStyle.SECONDARY,
            onClick = onPageRetryClick,
          )
          Text(
            text = stringResource(pageLoadingError),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.extended.textSecondary,
            style = MaterialTheme.typography.headlineLarge,
          )
        }
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
      longPressedMessageId = "",
      isPageLoading = false,
      pageLoadingError = null,
      onLongClick = {},
      onMenuItemClick = {},
      onMenuDismiss = {},
      onMessageRetryClick = {},
      onPageRetryClick = {},
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
