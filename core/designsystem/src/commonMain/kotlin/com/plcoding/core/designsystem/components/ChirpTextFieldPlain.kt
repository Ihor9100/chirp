package com.plcoding.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.ChirTheme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpTextFieldPlain(
  modifier: Modifier = Modifier,
  topTitle: String,
  textFieldState: TextFieldState,
  inputPlaceholder: String,
  keyboardType: KeyboardType,
  bottomTitle: String,
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
        if (textFieldState.text.isBlank() && inputPlaceholder.isNotBlank()) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
          ) {
            Text(
              text = inputPlaceholder,
              color = MaterialTheme.colorScheme.extended.textPlaceholder,
              style = MaterialTheme.typography.bodyMedium,
            )
          }
        }
        innerBox()
      }
    )
  }
}

@Composable
@Preview
fun ChirpTextFieldPlainEmptyPreview() {
  ChirTheme {
    ChirpTextFieldPlain(
      topTitle = "Hello",
      textFieldState = TextFieldState(""),
      inputPlaceholder = "Wowww",
      keyboardType = KeyboardType.Text,
      bottomTitle = "Tralalolo-Tralala",
    )
  }
}

@Composable
@Preview
fun ChirpTextFieldPlainFilledPreview() {
  ChirTheme {
    ChirpTextFieldPlain(
      topTitle = "Hello",
      textFieldState = TextFieldState("How are you?"),
      inputPlaceholder = "Wowww",
      keyboardType = KeyboardType.Text,
      bottomTitle = "Tralalolo-Tralala",
    )
  }
}

@Composable
@Preview
fun ChirpTextFieldPlainDisabledPreview() {
  ChirTheme {
    ChirpTextFieldPlain(
      topTitle = "Hello",
      textFieldState = TextFieldState("How are you?"),
      inputPlaceholder = "Wowww",
      keyboardType = KeyboardType.Text,
      bottomTitle = "Tralalolo-Tralala",
      isEnabled = false,
    )
  }
}

@Composable
@Preview
fun ChirpTextFieldPlainErrorPreview() {
  ChirTheme {
    ChirpTextFieldPlain(
      topTitle = "Hello",
      textFieldState = TextFieldState("How are you?"),
      inputPlaceholder = "Wowww",
      keyboardType = KeyboardType.Text,
      bottomTitle = "Tralalolo-Tralala",
      isError = true,
    )
  }
}