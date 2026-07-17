package com.plcoding.core.designsystem.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.model.MultilineTextFieldUi
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MultilineTextField(
  modifier: Modifier = Modifier,
  deviceConfiguration: DeviceConfiguration,
  multilineTextFieldPm: MultilineTextFieldUi,
  onClick:() -> Unit,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .then(
        if (deviceConfiguration.isWideScreen) {
          Modifier
            .shadow(
              elevation = 1.dp,
              shape = RoundedCornerShape(16.dp),
              clip = false,
            )
            .background(
              color = MaterialTheme.colorScheme.surface,
              shape = RoundedCornerShape(16.dp),
            )
            .padding(8.dp)
        } else {
          Modifier
        }
      )
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.extended.surfaceOutline,
        shape = RoundedCornerShape(16.dp)
      )
      .background(
        color = MaterialTheme.colorScheme.extended.surfaceLower,
        shape = RoundedCornerShape(16.dp),
      )
      .padding(
        horizontal = 16.dp,
        vertical = 12.dp,
      ),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    BasicTextField(
      state = multilineTextFieldPm.textFieldState,
      modifier = Modifier.fillMaxWidth(),
      textStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.extended.textPrimary
      ),
      cursorBrush = SolidColor(MaterialTheme.colorScheme.extended.textPrimary),
      decorator = { innerBox ->
        if (multilineTextFieldPm.textFieldState.text.isBlank()) {
          Text(
            text = stringResource(multilineTextFieldPm.inputPlaceholderRes),
            color = MaterialTheme.colorScheme.extended.textPlaceholder,
            style = MaterialTheme.typography.bodyMedium,
          )
        }
        innerBox()
      }
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      if (multilineTextFieldPm.connectionIconRes != null) {
        Icon(
          modifier = modifier.size(24.dp),
          imageVector = vectorResource(multilineTextFieldPm.connectionIconRes),
          contentDescription = null,
          tint = MaterialTheme.colorScheme.extended.textDisabled
        )
      }
      Button(
        text = stringResource(multilineTextFieldPm.buttonTitleRes),
        isEnabled = multilineTextFieldPm.isButtonEnabled,
        onClick = onClick,
      )
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
  deviceConfiguration: DeviceConfiguration,
  multilineTextFieldPm: MultilineTextFieldUi,
) {
  Theme(isDarkTheme) {
    MultilineTextField(
      deviceConfiguration = deviceConfiguration,
      multilineTextFieldPm = multilineTextFieldPm,
      onClick = {}
    )
  }
}

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
    multilineTextFieldPm = MultilineTextFieldUi.mock,
  )
}

@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
    multilineTextFieldPm = MultilineTextFieldUi.mock,
  )
}

@Composable
@Preview
private fun WideLightPreview() {
  Themed(
    isDarkTheme = false,
    deviceConfiguration = DeviceConfiguration.DESKTOP,
    multilineTextFieldPm = MultilineTextFieldUi.mock,
  )
}

@Composable
@Preview
private fun WideDarkPreview() {
  Themed(
    isDarkTheme = true,
    deviceConfiguration = DeviceConfiguration.DESKTOP,
    multilineTextFieldPm = MultilineTextFieldUi.mock,
  )
}
