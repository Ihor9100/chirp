package com.plcoding.feature.chat.presentation.screen.user.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserProfileAdaptiveSection(
  modifier: Modifier,
  sectionRes: StringResource,
  content: @Composable ColumnScope.() -> Unit,
) {
  val deviceConfiguration = getDeviceConfiguration()

  when (deviceConfiguration) {
    DeviceConfiguration.MOBILE_PORTRAIT -> Column {
      Text(
        modifier = Modifier.weight(1f),
        text = stringResource(sectionRes),
        color = MaterialTheme.colorScheme.extended.textTertiary,
        style = MaterialTheme.typography.labelSmall,
      )
      content()
    }
    else -> Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.Top,
    ) {
      Text(
        modifier = Modifier.weight(1f),
        text = stringResource(sectionRes),
        color = MaterialTheme.colorScheme.extended.textTertiary,
        style = MaterialTheme.typography.labelSmall,
      )
      Column(
        modifier = Modifier.weight(3f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
      ) {
        content()
      }
    }
  }
}
