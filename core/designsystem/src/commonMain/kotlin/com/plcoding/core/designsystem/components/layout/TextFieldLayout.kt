package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.extended

@Composable
fun TextFieldLayout(
  modifier: Modifier = Modifier,
  topTitle: String? = null,
  bottomTitle: String? = null,
  isError: Boolean = false,
  isEnabled: Boolean = true,
  onFocusChanged: ((isFocused: Boolean) -> Unit)? = null,
  textField: @Composable (TextStyle, MutableInteractionSource, isFocused: Boolean) -> Unit,
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isFocused by interactionSource.collectIsFocusedAsState()
  LaunchedEffect(isFocused) { onFocusChanged?.invoke(isFocused) }

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
    if (!topTitle.isNullOrBlank()) {
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

    if (!bottomTitle.isNullOrBlank()) {
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
