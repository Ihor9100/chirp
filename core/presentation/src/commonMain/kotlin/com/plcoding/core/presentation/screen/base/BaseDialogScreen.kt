package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.components.layout.adaptive.AdaptiveDialogSheetLayout
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.clearFocusOnTab
import com.plcoding.core.presentation.model.BaseUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseDialogScreen(
  modifier: Modifier = Modifier,
  baseUiState: BaseUiState,
  deviceConfiguration: DeviceConfiguration,
  onDismiss: () -> Unit,
  content: @Composable () -> Unit,
) {
  AdaptiveDialogSheetLayout(
    modifier = modifier
      .clearFocusOnTab()
      .imePadding(),
    onDismiss = onDismiss,
    containerColor = MaterialTheme.colorScheme.surface,
    deviceConfiguration = deviceConfiguration,
    windowInsets = BottomSheetDefaults.windowInsets,
  ) {
    BaseScreenOverlays(
      modifier = Modifier,
      baseUiState = baseUiState,
      content = content,
    )
  }
}
