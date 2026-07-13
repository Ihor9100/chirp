package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.feature.chat.presentation.model.ChatDetailsUi
import com.plcoding.feature.chat.presentation.model.DateDividerUi
import com.plcoding.feature.chat.presentation.model.LocalMessageUi
import com.plcoding.feature.chat.presentation.model.RemoteMessageUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatDetails(
  modifier: Modifier,
  chatDetailsUi: List<ChatDetailsUi>,
) {
  LazyColumn(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    items(chatDetailsUi) {
      when (it) {
        is DateDividerUi -> DateDivider(
          modifier = Modifier,
          dateDividerPm = it,
        )
        is LocalMessageUi -> LocalMessage(
          modifier = Modifier,
          localMessagePm = it,
        )
        is RemoteMessageUi -> RemoteMessage(
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
    ChatDetails(
      modifier = Modifier,
      chatDetailsUi = ChatDetailsUi.mocks,
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
