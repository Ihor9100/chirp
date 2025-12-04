package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.brand.ChirTitle
import com.plcoding.core.designsystem.components.brand.ChirpLogo
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.getDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

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
      ChirpLayout(
        modifier = modifier,
        logo = logo,
      ) {
        ChirTitle(
          title = title,
          titleColor = titleColor,
          error = error,
        )
        Spacer(Modifier.height(32.dp))
        form()
      }
    }
    DeviceConfiguration.MOBILE_LANDSCAPE -> {
      Row(
        modifier = modifier
          .background(MaterialTheme.colorScheme.background)
          .safeDrawingPadding(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
      ) {
        Column(
          modifier = Modifier
            .weight(1f)
            .fillMaxHeight(),
        ) {
          Spacer(Modifier.height(32.dp))
          ChirpLogo()
          Spacer(Modifier.height(32.dp))
          ChirTitle(
            title = title,
            titleColor = titleColor,
            error = error,
          )
        }
        ChirpLayout(
          modifier = Modifier.weight(1f),
          contentColumnTopSpaceDp = 20.dp,
          content = form
        )
      }
    }
    DeviceConfiguration.TABLET_PORTRAIT,
    DeviceConfiguration.TABLET_LANDSCAPE,
    DeviceConfiguration.DESKTOP -> {
      Column(
        modifier = modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.background)
          .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Spacer(Modifier.height(32.dp))
        logo()
        Spacer(Modifier.height(32.dp))
        Column(
          modifier = Modifier
            .widthIn(max = 400.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Spacer(Modifier.height(40.dp))
          ChirTitle(
            title = title,
            titleColor = titleColor,
            error = error,
          )
          Spacer(Modifier.height(32.dp))
          form()
          Spacer(Modifier.height(40.dp))
        }
      }
    }
  }
}

@Composable
fun ChirpAdaptiveFormLayoutThemed(
  isDarkTheme: Boolean,
  deviceConfiguration: DeviceConfiguration,
) {
  ChirpTheme(isDarkTheme) {
    ChirAdaptiveFormLayout(
      modifier = Modifier
        .fillMaxSize(),
      deviceConfiguration = deviceConfiguration,
      logo = ::ChirpLogo,
      title = "Hello World",
      error = "Invalid Data",
      form = {
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

@Composable
@Preview(
  widthDp = 450,
  heightDp = 1000,
)
fun ChirpAdaptiveFormLayoutMobilePortraitLightPreview() {
  ChirpAdaptiveFormLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 450,
  heightDp = 1000,
)
fun ChirpAdaptiveFormLayoutMobilePortraitDarkPreview() {
  ChirpAdaptiveFormLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 1000,
  heightDp = 450,
)
fun ChirpAdaptiveFormLayoutMobileLandscapeLightPreview() {
  ChirpAdaptiveFormLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 1000,
  heightDp = 450,
)
fun ChirpAdaptiveFormLayoutMobileLandscapeDarkPreview() {
  ChirpAdaptiveFormLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 750,
  heightDp = 1200,
)
fun ChirpAdaptiveFormLayoutTabletPortraitLightPreview() {
  ChirpAdaptiveFormLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 750,
  heightDp = 1200,
)
fun ChirpAdaptiveFormLayoutTabletPortraitDarkPreview() {
  ChirpAdaptiveFormLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.TABLET_PORTRAIT,
  )
}

@Composable
@Preview(
  widthDp = 1200,
  heightDp = 750,
)
fun ChirpAdaptiveFormLayoutTabletLandscapeLightPreview() {
  ChirpAdaptiveFormLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.TABLET_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 1200,
  heightDp = 750,
)
fun ChirpAdaptiveFormLayoutTabletLandscapeDarkPreview() {
  ChirpAdaptiveFormLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.TABLET_LANDSCAPE,
  )
}

@Composable
@Preview(
  widthDp = 2000,
  heightDp = 1500,
)
fun ChirpAdaptiveFormLayoutDesktopLightPreview() {
  ChirpAdaptiveFormLayoutThemed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.DESKTOP,
  )
}

@Composable
@Preview(
  widthDp = 2000,
  heightDp = 1500,
)
fun ChirpAdaptiveFormLayoutDesktopDarkPreview() {
  ChirpAdaptiveFormLayoutThemed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.DESKTOP,
  )
}
