package com.plcoding.core.designsystem.components.dialog

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.style.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
  modifier: Modifier = Modifier,
  onDismiss: () -> Unit,
  content: @Composable ColumnScope.() -> Unit,
) {
  ModalBottomSheet(
    modifier = modifier,
    onDismissRequest = onDismiss,
    dragHandle = null,
    content = content,
  )
}

@Composable
fun BottomSheetThemed(
  isDarkTheme: Boolean,
) {
  Theme(isDarkTheme) {
    BottomSheet(
      onDismiss = {},
      content = {},
    )
  }
}

@Composable
@Preview
fun LightPreview() {
  BottomSheetThemed(
    isDarkTheme = false,
  )
}


@Composable
@Preview
fun DarkPreview() {
  BottomSheetThemed(
    isDarkTheme = true,
  )
}
