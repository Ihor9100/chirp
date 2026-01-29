package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.layout.safeDrawingPadding

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveDialogSheetLayout
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.presentation.screen.model.BaseContentPm
import com.plcoding.core.presentation.utils.clearFocusOnTab

@Composable
fun BaseDialogScreen(
  modifier: Modifier = Modifier,
  baseContentPm: BaseContentPm,
  deviceConfiguration: DeviceConfiguration,
  onDismiss: () -> Unit,
  content: @Composable () -> Unit,
) {
  AdaptiveDialogSheetLayout(
    onDismiss = onDismiss,
    containerColor = MaterialTheme.colorScheme.surface,
    deviceConfiguration = deviceConfiguration,
  ) {
    BaseScreenOverlays(
      modifier = modifier
        .clearFocusOnTab()
        .safeDrawingPadding(),
      baseContentPm = baseContentPm,
      content = content,
    )
  }
}
