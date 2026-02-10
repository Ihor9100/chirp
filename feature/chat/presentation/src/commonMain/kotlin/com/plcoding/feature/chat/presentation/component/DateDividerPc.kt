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
import com.plcoding.core.designsystem.components.HorizontalDividerPc
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.feature.chat.presentation.model.DateDividerPm
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DateDividerPc(
  modifier: Modifier,
  dateDividerPm: DateDividerPm,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    HorizontalDividerPc(
      modifier = modifier.weight(1f)
    )
    Text(
      modifier = Modifier.padding(horizontal = 32.dp),
      text = dateDividerPm.date,
      color = MaterialTheme.colorScheme.extended.textPlaceholder,
      style = MaterialTheme.typography.labelSmall,
    )
    HorizontalDividerPc(
      modifier = modifier.weight(1f)
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    DateDividerPc(
      modifier = Modifier,
      dateDividerPm = DateDividerPm.mock,
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
