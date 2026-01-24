package com.plcoding.core.designsystem.components.layout.adaptive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.AppLogoPc
import com.plcoding.core.designsystem.components.layout.FormLayout
import com.plcoding.core.designsystem.components.layout.ResultLayoutMock
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AdaptiveResultLayout(
  modifier: Modifier = Modifier,
  deviceConfiguration: DeviceConfiguration = getDeviceConfiguration(),
  content: @Composable ColumnScope.() -> Unit,
) {
  if (deviceConfiguration == DeviceConfiguration.MOBILE_PORTRAIT) {
    FormLayout(
      modifier = modifier,
      contentColumnTopSpaceDp = 0.dp,
      logo = { AppLogoPc() },
    ) {
      content()
    }
  } else {
    Column(
      modifier = modifier,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      if (deviceConfiguration != DeviceConfiguration.MOBILE_LANDSCAPE) {
        Spacer(Modifier.height(32.dp))
        AppLogoPc()
      }
      Spacer(Modifier.height(32.dp))
      Column(
        modifier = Modifier
          .widthIn(max = 480.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(MaterialTheme.colorScheme.surface)
          .padding(horizontal = 16.dp)
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        content()
        Spacer(Modifier.height(16.dp))
      }
    }
  }
}

@Composable
fun AdaptiveResultLayoutThemed(
  isDarkTheme: Boolean,
  deviceConfiguration: DeviceConfiguration,
) {
  Theme(isDarkTheme) {
    AdaptiveResultLayout(
      modifier = Modifier
        .fillMaxSize(),
      deviceConfiguration = deviceConfiguration,
      content = {
        ResultLayoutMock()
      }
    )
  }
}

@Composable
@Preview(
  widthDp = 450,
  heightDp = 1000,
)
fun AdaptiveResultLayoutMobilePortraitLightPreview() {
  AdaptiveResultLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 450,
  heightDp = 1000,
)
fun AdaptiveResultLayoutMobilePortraitDarkPreview() {
  AdaptiveResultLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 1000,
  heightDp = 450,
)
fun AdaptiveResultLayoutMobileLandscapeLightPreview() {
  AdaptiveResultLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 1000,
  heightDp = 450,
)
fun AdaptiveResultLayoutMobileLandscapeDarkPreview() {
  AdaptiveResultLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 750,
  heightDp = 1200,
)
fun AdaptiveResultLayoutTabletPortraitLightPreview() {
  AdaptiveResultLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 750,
  heightDp = 1200,
)
fun AdaptiveResultLayoutTabletPortraitDarkPreview() {
  AdaptiveResultLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 1200,
  heightDp = 750,
)
fun AdaptiveResultLayoutTabletLandscapeLightPreview() {
  AdaptiveResultLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.TABLET_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 1200,
  heightDp = 750,
)
fun AdaptiveResultLayoutTabletLandscapeDarkPreview() {
  AdaptiveResultLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.TABLET_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 2000,
  heightDp = 1500,
)
fun AdaptiveResultLayoutDesktopLightPreview() {
  AdaptiveResultLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.DESKTOP,
  )
}

@Composable
@Preview(
  widthDp = 2000,
  heightDp = 1500,
)
fun AdaptiveResultLayoutDesktopDarkPreview() {
  AdaptiveResultLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.DESKTOP,
  )
}
