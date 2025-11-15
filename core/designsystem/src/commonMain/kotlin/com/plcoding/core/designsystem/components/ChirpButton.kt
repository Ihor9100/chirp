package com.plcoding.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.ChirTheme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ChirpButtonStyle {
  TEXT,
  PRIMARY,
  SECONDARY,
  DESTRUCTIVE_PRIMARY,
  DESTRUCTIVE_SECONDARY,
}

@Composable
fun ChirpButton(
  text: String,
  modifier: Modifier = Modifier,
  style: ChirpButtonStyle = ChirpButtonStyle.PRIMARY,
  isLoading: Boolean = false,
  enabled: Boolean = true,
  onClick: () -> Unit = {},
  leadingIcon: @Composable (() -> Unit)? = null,
) {
  val colors = when(style) {
    ChirpButtonStyle.TEXT -> ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary,
      disabledContainerColor = MaterialTheme.colorScheme.extended.disabledFill,
      disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
    )
    ChirpButtonStyle.PRIMARY -> TODO()
    ChirpButtonStyle.SECONDARY -> TODO()
    ChirpButtonStyle.DESTRUCTIVE_PRIMARY -> TODO()
    ChirpButtonStyle.DESTRUCTIVE_SECONDARY -> TODO()
  }

  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = RoundedCornerShape(8.dp)
  ) {
    Box(
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator(
        modifier = Modifier
          .size(15.dp)
          .alpha(if (isLoading) 1f else 0f),
        strokeWidth = 1.5.dp,
        color = Color.Black,
      )
      Row(
        horizontalArrangement = Arrangement.spacedBy(
          space = 8.dp,
          alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .alpha(if (isLoading) 0f else 1f)
      ) {
        leadingIcon?.invoke()
        Text(
          text = text,
          style = MaterialTheme.typography.titleSmall,
        )
      }
    }
  }
}

@Composable
@Preview
fun ChirpButtonTextPreview() {
  ChirTheme {
    ChirpButton(
      text = "Hello World!",
      style = ChirpButtonStyle.TEXT
    )
  }
}

@Composable
@Preview
fun ChirpButtonPrimaryPreview() {
  ChirTheme {
    ChirpButton(
      text = "Hello World!",
      style = ChirpButtonStyle.PRIMARY
    )
  }
}

@Composable
@Preview
fun ChirpButtonSecondaryPreview() {
  ChirTheme {
    ChirpButton(
      text = "Hello World!",
      style = ChirpButtonStyle.SECONDARY
    )
  }
}

@Composable
@Preview
fun ChirpButtonDestructivePrimaryPreview() {
  ChirTheme {
    ChirpButton(
      text = "Hello World!",
      style = ChirpButtonStyle.DESTRUCTIVE_PRIMARY
    )
  }
}

@Composable
@Preview
fun ChirpButtonDestructiveSecondaryPreview() {
  ChirTheme {
    ChirpButton(
      text = "Hello World!",
      style = ChirpButtonStyle.DESTRUCTIVE_SECONDARY
    )
  }
}
