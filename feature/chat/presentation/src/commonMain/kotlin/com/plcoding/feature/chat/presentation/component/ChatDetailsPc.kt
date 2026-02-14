package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.feature.chat.presentation.model.ChatDetailsPm
import com.plcoding.feature.chat.presentation.model.DateDividerPm
import com.plcoding.feature.chat.presentation.model.LocalMessagePm
import com.plcoding.feature.chat.presentation.model.RemoteMessagePm
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatDetailsPc(
  modifier: Modifier,
  chatMessagesPm: List<ChatDetailsPm>,
) {
  LazyColumn(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    items(chatMessagesPm) {
      when (it) {
        is DateDividerPm -> DateDividerPc(
          modifier = Modifier,
          dateDividerPm = it,
        )
        is LocalMessagePm -> LocalMessagePc(
          modifier = Modifier,
          localMessagePm = it,
        )
        is RemoteMessagePm -> RemoteMessagePc(
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
    ChatDetailsPc(
      modifier = Modifier,
      chatMessagesPm = ChatDetailsPm.mocks,
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
