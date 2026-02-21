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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.ic_cloud_off
import chirp.core.designsystem.generated.resources.sent
import com.plcoding.core.designsystem.components.button.ButtonPc
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import com.plcoding.core.designsystem.utils.getDeviceConfiguration
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MultilineTextField(
  modifier: Modifier = Modifier,
  deviceConfiguration: DeviceConfiguration = getDeviceConfiguration(),
  textFieldState: TextFieldState,
  inputPlaceholder: String,
  buttonTitleRes: StringResource,
  isEnabled: Boolean = true,
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
            .padding(16.dp)
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
      state = textFieldState,
      modifier = Modifier
        .fillMaxWidth(),
      enabled = isEnabled,
      textStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.extended.textPrimary
      ),
      cursorBrush = SolidColor(MaterialTheme.colorScheme.extended.textPrimary),
      decorator = { innerBox ->
        if (textFieldState.text.isBlank() && inputPlaceholder.isNotBlank()) {
          Text(
            text = inputPlaceholder,
            color = MaterialTheme.colorScheme.extended.textPlaceholder,
            style = MaterialTheme.typography.bodyMedium,
          )
        }
        innerBox()
      }
    )
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        modifier = modifier
          .size(24.dp),
        imageVector = vectorResource(Res.drawable.ic_cloud_off),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.extended.textDisabled
      )
      ButtonPc(
        text = stringResource(buttonTitleRes),
        isEnabled = isEnabled
      )
    }
  }
}

@Composable
fun MultilineTextFieldThemed(
  isDarkTheme: Boolean,
  isEnabled: Boolean,
  deviceConfiguration: DeviceConfiguration,
) {
  Theme(isDarkTheme) {
    MultilineTextField(
      textFieldState = TextFieldState(),
      inputPlaceholder = "Enter your message",
      buttonTitleRes = Res.string.sent,
      isEnabled = isEnabled,
      deviceConfiguration = deviceConfiguration,
    )
  }
}

@Composable
@Preview
fun EnabledNotDesktopLightPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = false,
    isEnabled = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview
fun DisabledNotDesktopLightPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = false,
    isEnabled = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview
fun EnabledDesktopLightPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = false,
    isEnabled = true,
    deviceConfiguration = DeviceConfiguration.DESKTOP
  )
}

@Composable
@Preview
fun DisabledDesktopLightPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = false,
    isEnabled = false,
    deviceConfiguration = DeviceConfiguration.DESKTOP
  )
}

@Composable
@Preview
fun EnabledNotDesktopDarkPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = true,
    isEnabled = true,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview
fun DisabledNotDesktopDarkPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = true,
    isEnabled = false,
    deviceConfiguration = DeviceConfiguration.MOBILE_PORTRAIT,
  )
}

@Composable
@Preview
fun EnabledDesktopDarkPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = true,
    isEnabled = true,
    deviceConfiguration = DeviceConfiguration.DESKTOP
  )
}

@Composable
@Preview
fun DisabledDesktopDarkPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = true,
    isEnabled = false,
    deviceConfiguration = DeviceConfiguration.DESKTOP
  )
}
