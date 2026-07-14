package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.HorizontalDivider
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DateDivider(
  modifier: Modifier,
  dateDividerPm: ChatMessageUi.DateDividerUi,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    HorizontalDivider(
      modifier = modifier.weight(1f)
    )
    Text(
      modifier = Modifier.padding(horizontal = 32.dp),
      text = dateDividerPm.date,
      color = MaterialTheme.colorScheme.extended.textPlaceholder,
      style = MaterialTheme.typography.labelSmall,
    )
    HorizontalDivider(
      modifier = modifier.weight(1f)
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    DateDivider(
      modifier = Modifier,
      dateDividerPm = ChatMessageUi.DateDividerUi.mock,
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
