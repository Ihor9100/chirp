package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.getDeviceConfiguration

@Composable
fun ChirAdaptiveFormLayout(
  deviceConfiguration: DeviceConfiguration = getDeviceConfiguration(),
  logo: @Composable () -> Unit,
  title: String,
  error: String,
  form: @Composable ColumnScope.() -> Unit,
) {
  val titleColor = if (deviceConfiguration == DeviceConfiguration.MOBILE_LANDSCAPE) {
    MaterialTheme.colorScheme.onBackground
  } else {
    MaterialTheme.colorScheme.extended.textPrimary
  }

}