package com.plcoding.core.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.ChirTheme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpTextField(
  modifier: Modifier,
  topTitle: String,
  textFieldState: TextFieldState,
  inputPlaceholder: String,
  keyboardType: KeyboardType,
  bottomTitle: String,
  isError: Boolean = false,
  isEnabled: Boolean = true,
  isSingleLineInput: Boolean = true,
) {
  Column(
    modifier = modifier,
  ) {
    Text(
      text = topTitle,
      color = MaterialTheme.colorScheme.extended.textSecondary,
      style = MaterialTheme.typography.labelSmall,
    )
    Spacer(
      modifier = Modifier
        .height(6.dp),
    )
    BasicTextField(
      state = textFieldState,
      enabled = isEnabled,
      textStyle = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onSurface,
      ),
      keyboardOptions = KeyboardOptions(
        keyboardType = keyboardType,
      ),
      lineLimits = if (isSingleLineInput) {
        TextFieldLineLimits.SingleLine
      } else {
        TextFieldLineLimits.Default
      },

      )
  }
}

@Composable
@Preview
fun ChirpTextFieldPreview() {
  ChirTheme {
    ChirpTextField()
  }
}