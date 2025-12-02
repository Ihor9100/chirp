package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
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
import com.plcoding.core.designsystem.components.brand.ChirpBrandLogo
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.getDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpAdaptiveResultLayout(
  modifier: Modifier = Modifier,
  deviceConfiguration: DeviceConfiguration = getDeviceConfiguration(),
  content: @Composable ColumnScope.() -> Unit,
) {
  if (deviceConfiguration == DeviceConfiguration.MOBILE_PORTRAIT) {
    ChirpLayout(
      modifier = modifier,
      contentColumnTopSpaceDp = 0.dp,
      logo = { ChirpBrandLogo() },
    ) {
      content()
    }
  } else {
    Column(
      modifier = modifier
        .background(MaterialTheme.colorScheme.background)
        .safeDrawingPadding(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      if (deviceConfiguration != DeviceConfiguration.MOBILE_LANDSCAPE) {
        Spacer(Modifier.height(32.dp))
        ChirpBrandLogo()
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
fun ChirpAdaptiveResultLayoutThemed(
  isDarkTheme: Boolean,
  deviceConfiguration: DeviceConfiguration,
) {
  ChirpTheme(isDarkTheme) {
    ChirpAdaptiveResultLayout(
      modifier = Modifier
        .fillMaxSize(),
      deviceConfiguration = deviceConfiguration,
      content = {
        ChirpResultLayoutMock()
      }
    )
  }
}

@Composable
@Preview(
  widthDp = 450,
  heightDp = 1000,
)
fun ChirpAdaptiveResultLayoutMobilePortraitLightPreview() {
  ChirpAdaptiveResultLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 450,
  heightDp = 1000,
)
fun ChirpAdaptiveResultLayoutMobilePortraitDarkPreview() {
  ChirpAdaptiveResultLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 1000,
  heightDp = 450,
)
fun ChirpAdaptiveResultLayoutMobileLandscapeLightPreview() {
  ChirpAdaptiveResultLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 1000,
  heightDp = 450,
)
fun ChirpAdaptiveResultLayoutMobileLandscapeDarkPreview() {
  ChirpAdaptiveResultLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 750,
  heightDp = 1200,
)
fun ChirpAdaptiveResultLayoutTabletPortraitLightPreview() {
  ChirpAdaptiveResultLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 750,
  heightDp = 1200,
)
fun ChirpAdaptiveResultLayoutTabletPortraitDarkPreview() {
  ChirpAdaptiveResultLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 1200,
  heightDp = 750,
)
fun ChirpAdaptiveResultLayoutTabletLandscapeLightPreview() {
  ChirpAdaptiveResultLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.TABLET_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 1200,
  heightDp = 750,
)
fun ChirpAdaptiveResultLayoutTabletLandscapeDarkPreview() {
  ChirpAdaptiveResultLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.TABLET_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 2000,
  heightDp = 1500,
)
fun ChirpAdaptiveResultLayoutDesktopLightPreview() {
  ChirpAdaptiveResultLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.DESKTOP,
  )
}

@Composable
@Preview(
  widthDp = 2000,
  heightDp = 1500,
)
fun ChirpAdaptiveResultLayoutDesktopDarkPreview() {
  ChirpAdaptiveResultLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.DESKTOP,
  )
}
