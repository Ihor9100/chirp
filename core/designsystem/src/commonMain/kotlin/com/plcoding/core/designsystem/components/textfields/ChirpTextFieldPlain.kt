package com.plcoding.core.designsystem.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.layout.ChirpTextFieldLayout
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpTextFieldPlain(
  modifier: Modifier = Modifier,
  topTitle: String,
  textFieldState: TextFieldState,
  inputPlaceholder: String,
  bottomTitle: String?,
  keyboardType: KeyboardType,
  isError: Boolean = false,
  isEnabled: Boolean = true,
  isSingleLineInput: Boolean = true,
  onFocusChanged: ((Boolean) -> Unit) = {},
) {
  ChirpTextFieldLayout(
    modifier = modifier,
    topTitle = topTitle,
    bottomTitle = bottomTitle,
    isError = isError,
    isEnabled = isEnabled,
    onFocusChanged = onFocusChanged,
  ) { inputTextStyle, interactionSource, isFocused ->
    BasicTextField(
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
      keyboardOptions = KeyboardOptions(
        keyboardType = keyboardType,
      ),
      lineLimits = if (isSingleLineInput) {
        TextFieldLineLimits.SingleLine
      } else {
        TextFieldLineLimits.Default
      },
      interactionSource = interactionSource,
      cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
      decorator = { innerBox ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
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
      }
    )
  }
}

@Composable
fun ChirpTextFieldPlainThemed(
  isDarkTheme: Boolean,
  initialText: String,
  isEnabled: Boolean,
  isError: Boolean,
) {
  ChirpTheme(isDarkTheme) {
    ChirpTextFieldPlain(
      topTitle = "Hello",
      textFieldState = TextFieldState(initialText),
      inputPlaceholder = "Enter here",
      bottomTitle = "Something happened",
      keyboardType = KeyboardType.Text,
      isError = isError,
      isEnabled = isEnabled,
    )
  }
}

@Composable
@Preview
fun ChirpTextFieldPlainEmptyLightPreview() {
  ChirpTextFieldPlainThemed(
    isDarkTheme = false,
    initialText = "",
    isError = false,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPlainEmptyDarkPreview() {
  ChirpTextFieldPlainThemed(
    isDarkTheme = true,
    initialText = "",
    isError = false,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPlainFilledLightPreview() {
  ChirpTextFieldPlainThemed(
    isDarkTheme = false,
    initialText = "How are you?",
    isError = false,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPlainFilledDarkPreview() {
  ChirpTextFieldPlainThemed(
    isDarkTheme = true,
    initialText = "How are you?",
    isError = false,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPlainDisabledLightPreview() {
  ChirpTextFieldPlainThemed(
    isDarkTheme = false,
    initialText = "How are you?",
    isError = false,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ChirpTextFieldPlainDisabledDarkPreview() {
  ChirpTextFieldPlainThemed(
    isDarkTheme = true,
    initialText = "How are you?",
    isError = false,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ChirpTextFieldPlainErrorLightPreview() {
  ChirpTextFieldPlainThemed(
    isDarkTheme = false,
    initialText = "How are you?",
    isError = true,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ChirpTextFieldPlainErrorDarkPreview() {
  ChirpTextFieldPlainThemed(
    isDarkTheme = true,
    initialText = "How are you?",
    isError = true,
    isEnabled = true,
  )
}
