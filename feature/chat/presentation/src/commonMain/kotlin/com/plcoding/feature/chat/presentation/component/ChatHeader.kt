package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.StackedAvatars
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.style.titleXSmall
import com.plcoding.feature.chat.presentation.model.ChatHeaderUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatHeader(
  modifier: Modifier,
  chatHeaderUi: ChatHeaderUi,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    StackedAvatars(
      avatarsUi = chatHeaderUi.avatarsUi,
    )
    Column {
      Text(
        text = chatHeaderUi.title.get(),
        color = MaterialTheme.colorScheme.extended.textPrimary,
        style = MaterialTheme.typography.titleXSmall,
      )
      chatHeaderUi.description?.let {
        Text(
          text = it.get(),
          color = MaterialTheme.colorScheme.extended.textPlaceholder,
          style = MaterialTheme.typography.bodySmall,
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
    ChatHeader(
      modifier = Modifier,
      chatHeaderUi = ChatHeaderUi.mock,
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
