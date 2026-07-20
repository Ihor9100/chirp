package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.HorizontalDivider
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.feature.chat.presentation.model.ChatUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Chat(
  modifier: Modifier,
  chatUi: ChatUi,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(IntrinsicSize.Min),
  ) {
    Column(
      modifier = modifier
        .fillMaxWidth()
        .weight(1f)
        .background(chatUi.backgroundColorToken.toColor())
        .padding(top = 16.dp)
        .then(if (chatUi.isLast) Modifier.padding(bottom = 16.dp) else Modifier),
      horizontalAlignment = Alignment.Start,
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      ChatHeader(
        modifier = Modifier,
        chatHeaderUi = chatUi.chatHeaderUi,
      )
      chatUi.content?.let {
        Text(
          modifier = Modifier.padding(horizontal = 16.dp),
          text = it.get(),
          maxLines = 3,
          color = MaterialTheme.colorScheme.extended.textSecondary,
          style = MaterialTheme.typography.bodySmall,
        )
      }
      if (!chatUi.isLast) {
        HorizontalDivider()
      }
    }
    if (chatUi.isSelected) {
      VerticalDivider(
        modifier = Modifier.fillMaxHeight(),
        thickness = 3.dp,
        color = MaterialTheme.colorScheme.primary
      )
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    Chat(
      modifier = Modifier,
      chatUi = ChatUi.mocks[0]
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
