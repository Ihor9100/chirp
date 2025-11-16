package com.plcoding.core.designsystem.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.ChirTheme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpTextFieldLayout(
  modifier: Modifier = Modifier,
  topTitle: String,
  bottomTitle: String,
  isError: Boolean = false,
  isEnabled: Boolean = true,
  onFocusChanged: ((Boolean) -> Unit) = {},
  textField: @Composable (TextStyle, MutableInteractionSource, isFocused: Boolean) -> Unit,
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isFocused by interactionSource.collectIsFocusedAsState()
  LaunchedEffect(isFocused) { onFocusChanged(isFocused) }

  val inputTextStyle = MaterialTheme.typography.bodyMedium.copy(
    color = if (isEnabled) {
      MaterialTheme.colorScheme.onSurface
    } else {
      MaterialTheme.colorScheme.extended.textPlaceholder
    }
  )

  Column(
    modifier = modifier,
  ) {
    if (topTitle.isNotBlank()) {
      Text(
        text = topTitle,
        color = MaterialTheme.colorScheme.extended.textSecondary,
        style = MaterialTheme.typography.labelSmall,
      )
      Spacer(
        modifier = Modifier
          .height(6.dp),
      )
    }

    textField(inputTextStyle, interactionSource, isFocused)

    if (bottomTitle.isNotBlank()) {
      Spacer(
        modifier = Modifier
          .height(4.dp),
      )
      Text(
        text = bottomTitle,
        color = if (isError) {
          MaterialTheme.colorScheme.error
        } else {
          MaterialTheme.colorScheme.extended.textTertiary
        },
        style = MaterialTheme.typography.bodySmall,
      )
    }
  }
}


@Composable
@Preview
fun ChirpTextFieldPasswordEmptyPreview() {
  ChirTheme {
    ChirpTextFieldPassword(
      topTitle = "Password",
      textFieldState = TextFieldState(""),
      inputPlaceholder = "Password",
      bottomTitle = "Create as secure as possible",
      isSecureMode = true,
    )
  }
}

@Composable
@Preview
fun ChirpTextFieldPasswordFilledPreview() {
  ChirTheme {
    ChirpTextFieldPassword(
      topTitle = "Password",
      textFieldState = TextFieldState("123456789"),
      inputPlaceholder = "Password",
      bottomTitle = "Create as secure as possible",
      isSecureMode = true,
    )
  }
}

@Composable
@Preview
fun ChirpTextFieldPasswordDisabledPreview() {
  ChirTheme {
    ChirpTextFieldPassword(
      topTitle = "Password",
      textFieldState = TextFieldState("123456789"),
      inputPlaceholder = "Password",
      bottomTitle = "Create as secure as possible",
      isEnabled = false,
      isSecureMode = true,
    )
  }
}

@Composable
@Preview
fun ChirpTextFieldPasswordErrorPreview() {
  ChirTheme {
    ChirpTextFieldPassword(
      topTitle = "Password",
      textFieldState = TextFieldState("123456789"),
      inputPlaceholder = "Password",
      bottomTitle = "Create as secure as possible",
      isError = true,
      isEnabled = false,
      isSecureMode = true,
    )
  }
}