@file:OptIn(ExperimentalMaterial3Api::class)

package com.plcoding.core.designsystem.components.dialog

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.plcoding.core.designsystem.style.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
  modifier: Modifier,
  containerColor: Color,
  windowInsets: WindowInsets,
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
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = containerColor,
    dragHandle = null,
    contentWindowInsets = { windowInsets },
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
      modifier = Modifier,
      containerColor = MaterialTheme.colorScheme.surface,
      windowInsets = BottomSheetDefaults.windowInsets,
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
