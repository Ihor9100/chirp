package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveDialogSheetLayout
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.clearFocusOnTab

@Composable
fun BaseScreenDialogContent(
  modifier: Modifier = Modifier,
  baseContent: BaseContent,
  deviceConfiguration: DeviceConfiguration,
  onDismiss: () -> Unit,
  content: @Composable () -> Unit,
) {
  AdaptiveDialogSheetLayout(
    onDismiss = onDismiss,
    containerColor = MaterialTheme.colorScheme.surface,
    deviceConfiguration = deviceConfiguration,
  ) {
    Box(
      modifier = modifier
        .clearFocusOnTab()
        .safeDrawingPadding(),
      contentAlignment = Alignment.Center,
    ) {
      content()

      Box(
        modifier = Modifier
          .matchParentSize()
          .background(Color.Black.copy(alpha = 0.2f))
          .pointerInput(Unit) {}
      )
      CircularProgressIndicator(
        modifier = Modifier.size(64.dp),
        color = MaterialTheme.colorScheme.primary,
      )
      baseContent.overlays?.forEach { overlay ->
        when (overlay) {
          Overlay.BLOCKABLE -> {
          }
          Overlay.LOADABLE -> {
          }
        }
      }
    }
  }
}
