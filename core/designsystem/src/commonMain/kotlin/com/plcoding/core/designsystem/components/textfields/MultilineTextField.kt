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
import com.plcoding.core.designsystem.components.button.Button
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MultilineTextField(
  modifier: Modifier = Modifier,
  textFieldState: TextFieldState,
  inputPlaceholder: String,
  buttonTitleRes: StringResource,
  isEnabled: Boolean = true,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .shadow(
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        clip = false,
      )
      .background(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
      )
      .padding(4.dp)
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
      Button(
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
) {
  Theme(isDarkTheme) {
    MultilineTextField(
      textFieldState = TextFieldState(),
      inputPlaceholder = "Enter your message",
      buttonTitleRes = Res.string.sent,
      isEnabled = isEnabled,
    )
  }
}

@Composable
@Preview
fun MultilineTextFieldEnabledLightPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = false,
    isEnabled = true
  )
}

@Composable
@Preview
fun MultilineTextFieldDisabledLightPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = false,
    isEnabled = false
  )
}

@Composable
@Preview
fun MultilineTextFieldEnabledDarkPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = true,
    isEnabled = true
  )
}

@Composable
@Preview
fun MultilineTextFieldDisabledDarkPreview() {
  MultilineTextFieldThemed(
    isDarkTheme = true,
    isEnabled = false
  )
}
