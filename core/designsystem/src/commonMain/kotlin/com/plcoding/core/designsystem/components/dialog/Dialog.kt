package com.plcoding.core.designsystem.components.dialog

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.plcoding.core.designsystem.style.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Dialog(
  modifier: Modifier = Modifier,
  onDismiss: () -> Unit,
  content: @Composable () -> Unit,
) {
  Dialog(
    onDismissRequest = onDismiss,
  ) {
    Surface(
      modifier = modifier,
      shape = RoundedCornerShape(16.dp),
      color = MaterialTheme.colorScheme.surface
    ) {
      content()
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  Theme(isDarkTheme) {
    Dialog(
      onDismiss = {},
      content = {},
    )
  }
}

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkTheme = false,
  )
}


@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkTheme = true,
  )
}
