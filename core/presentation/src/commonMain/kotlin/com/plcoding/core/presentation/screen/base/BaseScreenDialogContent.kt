package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.layout.safeDrawingPadding

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    BaseScreenOverlaysContent(
      modifier = modifier
        .clearFocusOnTab()
        .safeDrawingPadding(),
      baseContent = baseContent,
      content = content,
    )
  }
}
