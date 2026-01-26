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
import com.plcoding.core.designsystem.components.HorizontalDividerPc
import com.plcoding.core.designsystem.components.StackedAvatars
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.style.getColor
import com.plcoding.core.designsystem.style.titleXSmall
import com.plcoding.feature.chat.presentation.model.ChatPm
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatPc(
  modifier: Modifier,
  chatPm: ChatPm,
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
        .background(chatPm.backgroundColorToken.getColor())
        .padding(top = 16.dp),
      horizontalAlignment = Alignment.Start,
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp),
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
          modifier = Modifier.padding(horizontal = 16.dp),
          text = it.get(),
          maxLines = 3,
          color = MaterialTheme.colorScheme.extended.textSecondary,
          style = MaterialTheme.typography.bodySmall,
        )
      }
      if (chatPm.showHorizontalDivider) {
        HorizontalDividerPc()
      }
    }
    if (chatPm.showVerticalDivider) {
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
