package com.plcoding.core.designsystem.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.hide_password
import chirp.core.designsystem.generated.resources.ic_eye_off
import chirp.core.designsystem.generated.resources.ic_eye_on
import chirp.core.designsystem.generated.resources.show_password
import com.plcoding.core.designsystem.components.layout.ChirpTextFieldLayout
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpTextFieldPassword(
  modifier: Modifier = Modifier,
  topTitle: String,
  textFieldState: TextFieldState,
  inputPlaceholder: String,
  bottomTitle: String?,
  isError: Boolean = false,
  isEnabled: Boolean = true,
  isSecureMode: Boolean = false,
  onFocusChanged: ((Boolean) -> Unit) = {},
  onSecureToggleClick: (() -> Unit) = {},
) {
  ChirpTextFieldLayout(
    modifier = modifier,
    topTitle = topTitle,
    bottomTitle = bottomTitle,
    isError = isError,
    isEnabled = isEnabled,
    onFocusChanged = onFocusChanged,
  ) { inputTextStyle, interactionSource, isFocused ->
    BasicSecureTextField(
      state = textFieldState,
      modifier = Modifier
        .fillMaxWidth()
        .background(
          color = when {
            isFocused -> MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
            isEnabled -> MaterialTheme.colorScheme.surface
            else -> MaterialTheme.colorScheme.extended.secondaryFill
          },
          shape = RoundedCornerShape(8.dp)
        )
        .border(
          width = 1.dp,
          color = when {
            isFocused -> MaterialTheme.colorScheme.primary
            isError -> MaterialTheme.colorScheme.error
            else -> MaterialTheme.colorScheme.outline
          },
          shape = RoundedCornerShape(8.dp),
        )
        .padding(12.dp),
      enabled = isEnabled,
      textStyle = inputTextStyle,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      interactionSource = interactionSource,
      cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
      decorator = { innerBox ->
        Row(
          modifier = modifier.fillMaxWidth(),
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
          ) {
            if (textFieldState.text.isBlank() && inputPlaceholder.isNotBlank()) {
              Text(
                text = inputPlaceholder,
                color = MaterialTheme.colorScheme.extended.textPlaceholder,
                style = MaterialTheme.typography.bodyMedium,
              )
            }
            innerBox()
          }

          Spacer(Modifier.width(8.dp))

          Icon(
            imageVector = vectorResource(
              if (isSecureMode) {
                Res.drawable.ic_eye_on
              } else {
                Res.drawable.ic_eye_off
              }
            ),
            contentDescription = stringResource(
              if (isSecureMode) {
                Res.string.show_password
              } else {
                Res.string.hide_password
              }
            ),
            modifier = Modifier
              .size(24.dp)
              .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                  bounded = false,
                  radius = 24.dp,
                ),
                onClick = onSecureToggleClick,
              ),
            tint = MaterialTheme.colorScheme.extended.textDisabled,
          )
        }
      }
    )
  }
}


@Composable
fun ChirpTextFieldPasswordThemed(
  isDarkTheme: Boolean,
  initialText: String,
  isError: Boolean,
  isEnabled: Boolean,
  isSecureMode: Boolean,
) {
  ChirpTheme(isDarkTheme) {
    ChirpTextFieldPassword(
      topTitle = "Password",
      textFieldState = TextFieldState(initialText),
      inputPlaceholder = "Password",
      bottomTitle = "Create as secure as possible",
      isError = isError,
      isEnabled = isEnabled,
      isSecureMode = isSecureMode,
    )
  }
}

@Composable
@Preview
fun ChirpTextFieldPasswordEmptyLightPreview() {
  ChirpTextFieldPasswordThemed(
    isDarkTheme = false,
    initialText = "",
    isError = false,
    isEnabled = true,
    isSecureMode = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPasswordEmptyDarkPreview() {
  ChirpTextFieldPasswordThemed(
    isDarkTheme = true,
    initialText = "",
    isError = false,
    isEnabled = true,
    isSecureMode = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPasswordFilledLightPreview() {
  ChirpTextFieldPasswordThemed(
    isDarkTheme = false,
    initialText = "123456789",
    isError = false,
    isEnabled = true,
    isSecureMode = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPasswordFilledDarkPreview() {
  ChirpTextFieldPasswordThemed(
    isDarkTheme = true,
    initialText = "123456789",
    isError = false,
    isEnabled = true,
    isSecureMode = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPasswordDisabledLightPreview() {
  ChirpTextFieldPasswordThemed(
    isDarkTheme = false,
    initialText = "123456789",
    isError = false,
    isEnabled = false,
    isSecureMode = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPasswordDisabledDarkPreview() {
  ChirpTextFieldPasswordThemed(
    isDarkTheme = true,
    initialText = "123456789",
    isError = false,
    isEnabled = false,
    isSecureMode = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPasswordErrorLightPreview() {
  ChirpTextFieldPasswordThemed(
    isDarkTheme = false,
    initialText = "123456789",
    isError = true,
    isEnabled = true,
    isSecureMode = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPasswordErrorDarkPreview() {
  ChirpTextFieldPasswordThemed(
    isDarkTheme = true,
    initialText = "123456789",
    isError = true,
    isEnabled = true,
    isSecureMode = true,
  )
}