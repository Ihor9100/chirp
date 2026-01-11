package com.plcoding.core.designsystem.components.dialog

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.style.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
  modifier: Modifier = Modifier,
  onDismiss: () -> Unit,
  content: @Composable () -> Unit,
) {
  val sheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = true
  )
  LaunchedEffect(sheetState.isVisible) {
    if (sheetState.isVisible) {
      sheetState.expand()
    }
  }

  ModalBottomSheet(
    modifier = modifier,
    sheetState = sheetState,
    onDismissRequest = onDismiss,
    dragHandle = null,
  ) {
    content()
  }
}

@Composable
private fun Themed(
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
