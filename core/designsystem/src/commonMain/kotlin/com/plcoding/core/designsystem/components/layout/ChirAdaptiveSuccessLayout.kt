package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.brand.ChirpBrandLogo
import com.plcoding.core.designsystem.components.surface.ChirpSurface
import com.plcoding.core.designsystem.style.ChirTheme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.getDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirAdaptiveSuccessLayout(
  modifier: Modifier = Modifier,
  deviceConfiguration: DeviceConfiguration = getDeviceConfiguration(),
  content: @Composable ColumnScope.() -> Unit,
) {
  if (deviceConfiguration == DeviceConfiguration.MOBILE_PORTRAIT) {
    ChirpSurface(
      modifier = modifier,
      logo = {
        Spacer(Modifier.height(32.dp))
        ChirpBrandLogo()
        Spacer(Modifier.height(32.dp))
      },
    ) {
      Spacer(Modifier.height(32.dp))
      content()
    }
  } else {
    Column(
      modifier = modifier
        .background(MaterialTheme.colorScheme.background),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      if (deviceConfiguration != DeviceConfiguration.MOBILE_LANDSCAPE) {
        Spacer(Modifier.height(32.dp))
        ChirpBrandLogo()
      }
      Spacer(Modifier.height(32.dp))
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .widthIn(max = 480.dp)
          .padding(horizontal = 40.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(MaterialTheme.colorScheme.surface)
          .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        content()
      }
    }
  }
}

@Composable
@Preview
fun ChirpAdaptiveSuccessMobilePortraitLightPreview() {
  ChirpAdaptiveSuccessPreview(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview
fun ChirpAdaptiveSuccessMobilePortraitDarkPreview() {
  ChirpAdaptiveSuccessPreview(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 650,
  heightDp = 300,
)
fun ChirpAdaptiveSuccessMobileLandscapeLightPreview() {
  ChirpAdaptiveSuccessPreview(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 650,
  heightDp = 300,
)
fun ChirpAdaptiveSuccessMobileLandscapeDarkPreview() {
  ChirpAdaptiveSuccessPreview(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 650,
  heightDp = 1000,
)
fun ChirpAdaptiveSuccessTabletPortraitLightPreview() {
  ChirpAdaptiveSuccessPreview(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 650,
  heightDp = 1000,
)
fun ChirpAdaptiveSuccessTabletPortraitDarkPreview() {
  ChirpAdaptiveSuccessPreview(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 1000,
  heightDp = 650,
)
fun ChirpAdaptiveSuccessTabletLandscapeLightPreview() {
  ChirpAdaptiveSuccessPreview(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.TABLET_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 1000,
  heightDp = 650,
)
fun ChirpAdaptiveSuccessTabletLandscapeDarkPreview() {
  ChirpAdaptiveSuccessPreview(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.TABLET_LANDSCAPE,
  )
}

@Composable
fun ChirpAdaptiveSuccessPreview(
  isDarkTheme: Boolean,
  deviceConfiguration: DeviceConfiguration,
) {
  ChirTheme(isDarkTheme) {
    ChirAdaptiveSuccessLayout(
      modifier = Modifier
        .fillMaxSize(),
      deviceConfiguration = deviceConfiguration,
      content = {
        Text(
          text = "Name",
          color = MaterialTheme.colorScheme.extended.textPrimary,
          style = MaterialTheme.typography.bodySmall,
        )
        Text(
          text = "Surname",
          color = MaterialTheme.colorScheme.extended.textPrimary,
          style = MaterialTheme.typography.bodySmall,
        )
        Text(
          text = "Address",
          color = MaterialTheme.colorScheme.extended.textPrimary,
          style = MaterialTheme.typography.bodySmall,
        )
        Text(
          text = "Age",
          color = MaterialTheme.colorScheme.extended.textPrimary,
          style = MaterialTheme.typography.bodySmall,
        )
      }
    )
  }
}
