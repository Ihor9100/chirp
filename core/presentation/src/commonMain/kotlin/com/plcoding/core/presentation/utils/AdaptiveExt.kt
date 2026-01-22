package com.plcoding.core.presentation.utils

import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.utils.DeviceConfiguration

@Composable
fun getPaneScaffoldDirective(
  deviceConfiguration: DeviceConfiguration,
  windowAdaptiveInfo: WindowAdaptiveInfo,
): PaneScaffoldDirective {
  val isTabletop = windowAdaptiveInfo.windowPosture.isTabletop

  return PaneScaffoldDirective(
    maxHorizontalPartitions = when (deviceConfiguration) {
      DeviceConfiguration.MOBILE_PORTRAIT,
      DeviceConfiguration.MOBILE_LANDSCAPE,
      DeviceConfiguration.TABLET_PORTRAIT -> 1
      DeviceConfiguration.TABLET_LANDSCAPE,
      DeviceConfiguration.DESKTOP -> 2
    },
    horizontalPartitionSpacerSize = 0.dp,
    maxVerticalPartitions = if (isTabletop) 2 else 1,
    verticalPartitionSpacerSize = if (isTabletop) 24.dp else 0.dp,
    defaultPanePreferredWidth = 360.dp,
    excludedBounds = emptyList(),
  )
}
