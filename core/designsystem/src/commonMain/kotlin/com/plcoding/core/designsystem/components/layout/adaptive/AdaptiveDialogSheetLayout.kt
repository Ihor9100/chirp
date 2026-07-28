package com.plcoding.core.designsystem.components.layout.adaptive

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.plcoding.core.designsystem.components.dialog.BottomSheet
import com.plcoding.core.designsystem.components.dialog.Dialog
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveDialogSheetLayout(
  modifier: Modifier = Modifier,
  containerColor: Color,
  deviceConfiguration: DeviceConfiguration,
  windowInsets: WindowInsets,
  onDismiss: () -> Unit,
  content: @Composable () -> Unit,
) {
  if (deviceConfiguration.isMobile) {
    BottomSheet(
      modifier = modifier,
      containerColor = containerColor,
      windowInsets = windowInsets,
      onDismiss = onDismiss,
      content = content,
    )
  } else {
    Dialog(
      modifier = modifier,
      containerColor = containerColor,
      onDismiss = onDismiss,
      content = content,
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
  deviceConfiguration: DeviceConfiguration,
) {
  Theme(isDarkTheme) {
    AdaptiveDialogSheetLayout(
      modifier = Modifier,
      containerColor = MaterialTheme.colorScheme.surface,
      deviceConfiguration = deviceConfiguration,
      windowInsets = WindowInsets(),
      onDismiss = {},
      content = {},
    )
  }
}

@Composable
@Preview(
  widthDp = 450,
  heightDp = 1000,
)
fun MobilePreview() {
  Themed(
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 750,
  heightDp = 1200,
)
fun TabletPreview() {
  Themed(
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}
