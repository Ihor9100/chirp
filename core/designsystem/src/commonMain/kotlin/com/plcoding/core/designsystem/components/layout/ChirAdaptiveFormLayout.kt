package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.brand.ChirBrandLogo
import com.plcoding.core.designsystem.components.brand.ChirBrandTitle
import com.plcoding.core.designsystem.components.surface.ChirpSurface
import com.plcoding.core.designsystem.style.ChirTheme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.getDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.log

@Composable
fun ChirAdaptiveFormLayout(
  modifier: Modifier = Modifier,
  deviceConfiguration: DeviceConfiguration = getDeviceConfiguration(),
  logo: @Composable () -> Unit,
  title: String,
  error: String?,
  form: @Composable ColumnScope.() -> Unit,
) {
  val titleColor = if (deviceConfiguration == DeviceConfiguration.MOBILE_LANDSCAPE) {
    MaterialTheme.colorScheme.onBackground
  } else {
    MaterialTheme.colorScheme.extended.textPrimary
  }

  when (deviceConfiguration) {
    DeviceConfiguration.MOBILE_PORTRAIT -> {
      ChirpSurface(
        modifier = modifier,
        logo = logo,
      ) {
        ChirBrandTitle(
          modifier = Modifier,
          title = title,
          titleColor = titleColor,
          error = error,
        )
        form()
      }
    }
    DeviceConfiguration.MOBILE_LANDSCAPE -> {
      TODO()
    }
    DeviceConfiguration.TABLET_PORTRAIT,
    DeviceConfiguration.TABLET_LANDSCAPE,
    DeviceConfiguration.DESKTOP -> {
      TODO()
    }
  }
}


@Composable
@Preview
fun ChirpAdaptiveFormPortraitLightPreview() {
  ChirpAdaptiveFormPreview(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview
fun ChirpAdaptiveFormPortraitDarkPreview() {
  ChirpAdaptiveFormPreview(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
fun ChirpAdaptiveFormPreview(
  isDarkTheme: Boolean,
  deviceConfiguration: DeviceConfiguration,
) {
  ChirTheme(isDarkTheme) {
    ChirAdaptiveFormLayout(
      modifier = Modifier
        .fillMaxSize(),
      deviceConfiguration = deviceConfiguration,
      logo = {
        ChirBrandLogo()
      },
      title = "Hello World",
      error = null,
      form = {
        Text(
          text = "Test",
          color = MaterialTheme.colorScheme.extended.textPrimary,
          style = MaterialTheme.typography.bodySmall,
        )
      }
    )
  }
}
