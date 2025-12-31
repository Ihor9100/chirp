package com.plcoding.core.designsystem.components.button

import androidx.compose.foundation.BorderStroke
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
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ButtonStyle {
  TEXT,
  PRIMARY,
  SECONDARY,
  DESTRUCTIVE_PRIMARY,
  DESTRUCTIVE_SECONDARY,
}

@Composable
fun Button(
  modifier: Modifier = Modifier,
  text: String,
  style: ButtonStyle = ButtonStyle.PRIMARY,
  isLoading: Boolean = false,
  enabled: Boolean = true,
  onClick: () -> Unit = {},
  leadingIcon: @Composable (() -> Unit)? = null,
) {
  val colors = when (style) {
    ButtonStyle.TEXT -> ButtonDefaults.buttonColors(
      containerColor = Color.Transparent,
      contentColor = MaterialTheme.colorScheme.tertiary,
      disabledContainerColor = Color.Transparent,
      disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
    )
    ButtonStyle.PRIMARY -> ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary,
      disabledContainerColor = MaterialTheme.colorScheme.extended.disabledFill,
      disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
    )
    ButtonStyle.SECONDARY -> ButtonDefaults.buttonColors(
      containerColor = Color.Transparent,
      contentColor = MaterialTheme.colorScheme.extended.textSecondary,
      disabledContainerColor = Color.Transparent,
      disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
    )
    ButtonStyle.DESTRUCTIVE_PRIMARY -> ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.error,
      contentColor = MaterialTheme.colorScheme.onError,
      disabledContainerColor = MaterialTheme.colorScheme.extended.disabledFill,
      disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
    )
    ButtonStyle.DESTRUCTIVE_SECONDARY -> ButtonDefaults.buttonColors(
      containerColor = Color.Transparent,
      contentColor = MaterialTheme.colorScheme.error,
      disabledContainerColor = Color.Transparent,
      disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
    )
  }

  val defaultBorderColor = BorderStroke(
    width = 1.dp,
    color = MaterialTheme.colorScheme.extended.disabledOutline,
  )
  val borderStroke = when {
    style == ButtonStyle.PRIMARY && !enabled -> defaultBorderColor
    style == ButtonStyle.SECONDARY -> defaultBorderColor
    style == ButtonStyle.DESTRUCTIVE_PRIMARY && !enabled -> defaultBorderColor
    style == ButtonStyle.DESTRUCTIVE_SECONDARY && !enabled -> {
      val color = if (enabled) {
        MaterialTheme.colorScheme.extended.destructiveSecondaryOutline
      } else {
        MaterialTheme.colorScheme.extended.disabledOutline
      }
      BorderStroke(
        width = 1.dp,
        color = color,
      )
    }
    else -> null
  }

  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = RoundedCornerShape(8.dp),
    colors = colors,
    border = borderStroke,
  ) {
    Box(
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator(
        modifier = Modifier.size(15.dp).alpha(if (isLoading) 1f else 0f),
        strokeWidth = 1.5.dp,
        color = Color.Black,
      )
      Row(
        horizontalArrangement = Arrangement.spacedBy(
          space = 8.dp,
          alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.alpha(if (isLoading) 0f else 1f)
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
fun ButtonThemed(
  isDarkTheme: Boolean,
  style: ButtonStyle,
  isEnabled: Boolean,
) {
  Theme(isDarkTheme) {
    Button(
      text = "Click",
      style = style,
      enabled = isEnabled,
    )
  }
}

@Composable
@Preview
fun ButtonTextEnabledLightPreview() {
  ButtonThemed(
    isDarkTheme = false,
    style = ButtonStyle.TEXT,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ButtonTextDisabledLightPreview() {
  ButtonThemed(
    isDarkTheme = false,
    style = ButtonStyle.TEXT,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ButtonTextEnabledDarkPreview() {
  ButtonThemed(
    isDarkTheme = true,
    style = ButtonStyle.TEXT,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ButtonTextDisabledDarkPreview() {
  ButtonThemed(
    isDarkTheme = true,
    style = ButtonStyle.TEXT,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ButtonPrimaryEnabledLightPreview() {
  ButtonThemed(
    isDarkTheme = false,
    style = ButtonStyle.PRIMARY,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ButtonPrimaryDisabledLightPreview() {
  ButtonThemed(
    isDarkTheme = false,
    style = ButtonStyle.PRIMARY,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ButtonPrimaryEnabledDarkPreview() {
  ButtonThemed(
    isDarkTheme = true,
    style = ButtonStyle.PRIMARY,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ButtonPrimaryDisabledDarkPreview() {
  ButtonThemed(
    isDarkTheme = true,
    style = ButtonStyle.PRIMARY,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ButtonSecondaryEnabledLightPreview() {
  ButtonThemed(
    isDarkTheme = false,
    style = ButtonStyle.SECONDARY,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ButtonSecondaryDisabledLightPreview() {
  ButtonThemed(
    isDarkTheme = false,
    style = ButtonStyle.SECONDARY,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ButtonSecondaryEnabledDarkPreview() {
  ButtonThemed(
    isDarkTheme = true,
    style = ButtonStyle.SECONDARY,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ButtonSecondaryDisabledDarkPreview() {
  ButtonThemed(
    isDarkTheme = true,
    style = ButtonStyle.SECONDARY,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ButtonDestructivePrimaryEnabledLightPreview() {
  ButtonThemed(
    isDarkTheme = false,
    style = ButtonStyle.DESTRUCTIVE_PRIMARY,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ButtonDestructivePrimaryDisabledLightPreview() {
  ButtonThemed(
    isDarkTheme = false,
    style = ButtonStyle.DESTRUCTIVE_PRIMARY,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ButtonDestructivePrimaryEnabledDarkPreview() {
  ButtonThemed(
    isDarkTheme = true,
    style = ButtonStyle.DESTRUCTIVE_PRIMARY,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ButtonDestructivePrimaryDisabledDarkPreview() {
  ButtonThemed(
    isDarkTheme = true,
    style = ButtonStyle.DESTRUCTIVE_PRIMARY,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ButtonDestructiveSecondaryEnabledLightPreview() {
  ButtonThemed(
    isDarkTheme = false,
    style = ButtonStyle.DESTRUCTIVE_SECONDARY,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ButtonDestructiveSecondaryDisabledLightPreview() {
  ButtonThemed(
    isDarkTheme = false,
    style = ButtonStyle.DESTRUCTIVE_SECONDARY,
    isEnabled = false,
  )
}

@Composable
@Preview
fun ButtonDestructiveSecondaryEnabledDarkPreview() {
  ButtonThemed(
    isDarkTheme = true,
    style = ButtonStyle.DESTRUCTIVE_SECONDARY,
    isEnabled = true,
  )
}

@Composable
@Preview
fun ButtonDestructiveSecondaryDisabledDarkPreview() {
  ButtonThemed(
    isDarkTheme = true,
    style = ButtonStyle.DESTRUCTIVE_SECONDARY,
    isEnabled = false,
  )
}
