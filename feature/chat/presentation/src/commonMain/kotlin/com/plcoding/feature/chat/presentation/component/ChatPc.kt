package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.HorizontalDividerPc
import com.plcoding.core.designsystem.components.StackedAvatars
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.style.titleXSmall
import com.plcoding.feature.chat.presentation.model.ChatPm
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatPc(
  modifier: Modifier,
  chatPm: ChatPm,
) {
  val backgroundColor: Color
  val verticalDividerColor: Color?

  if (chatPm.isSelected) {
    backgroundColor = MaterialTheme.colorScheme.surface
    verticalDividerColor = MaterialTheme.colorScheme.primary
  } else {
    backgroundColor = MaterialTheme.colorScheme.background
    verticalDividerColor = null
  }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(IntrinsicSize.Min)
      .background(backgroundColor),
  ) {
    Column(
      modifier = modifier
        .fillMaxWidth()
        .weight(1f),
      horizontalAlignment = Alignment.Start,
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        StackedAvatars(
          avatarsPm = chatPm.avatarsPm,
        )
        Column {
          Text(
            text = chatPm.title.get(),
            color = MaterialTheme.colorScheme.extended.textPrimary,
            style = MaterialTheme.typography.titleXSmall,
          )
          chatPm.description?.let {
            Text(
              text = it.get(),
              color = MaterialTheme.colorScheme.extended.textPlaceholder,
              style = MaterialTheme.typography.bodySmall,
            )
          }
        }
      }
      chatPm.content?.let {
        Text(
          text = it.get(),
          maxLines = 3,
          color = MaterialTheme.colorScheme.extended.textSecondary,
          style = MaterialTheme.typography.bodySmall,
        )
      }
    }
    verticalDividerColor?.let {
      VerticalDivider(
        modifier = Modifier
          .width(1.dp)
          .fillMaxHeight(),
        color = it
      )
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    ChatPc(
      modifier = Modifier,
      chatPm = ChatPm.mocks[0]
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
