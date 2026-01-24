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

enum class ButtonPcStyle {
  TEXT,
  PRIMARY,
  SECONDARY,
  DESTRUCTIVE_PRIMARY,
  DESTRUCTIVE_SECONDARY,
}

@Composable
fun ButtonPc(
  modifier: Modifier = Modifier,
  text: String,
  style: ButtonPcStyle = ButtonPcStyle.PRIMARY,
  isLoading: Boolean = false,
  isEnabled: Boolean = true,
  onClick: () -> Unit = {},
  leadingIcon: @Composable (() -> Unit)? = null,
) {
  val colors = when (style) {
    ButtonPcStyle.TEXT -> ButtonDefaults.buttonColors(
      containerColor = Color.Transparent,
      contentColor = MaterialTheme.colorScheme.tertiary,
      disabledContainerColor = Color.Transparent,
      disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
    )
    ButtonPcStyle.PRIMARY -> ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary,
      disabledContainerColor = MaterialTheme.colorScheme.extended.disabledFill,
      disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
    )
    ButtonPcStyle.SECONDARY -> ButtonDefaults.buttonColors(
      containerColor = Color.Transparent,
      contentColor = MaterialTheme.colorScheme.extended.textSecondary,
      disabledContainerColor = Color.Transparent,
      disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
    )
    ButtonPcStyle.DESTRUCTIVE_PRIMARY -> ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.error,
      contentColor = MaterialTheme.colorScheme.onError,
      disabledContainerColor = MaterialTheme.colorScheme.extended.disabledFill,
      disabledContentColor = MaterialTheme.colorScheme.extended.textDisabled,
    )
    ButtonPcStyle.DESTRUCTIVE_SECONDARY -> ButtonDefaults.buttonColors(
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
    style == ButtonPcStyle.PRIMARY && !isEnabled -> defaultBorderColor
    style == ButtonPcStyle.SECONDARY -> defaultBorderColor
    style == ButtonPcStyle.DESTRUCTIVE_PRIMARY && !isEnabled -> defaultBorderColor
    style == ButtonPcStyle.DESTRUCTIVE_SECONDARY && !isEnabled -> {
      val color = if (isEnabled) {
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
    enabled = isEnabled,
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
private fun ButtonPcThemed(
  isDarkTheme: Boolean,
  style: ButtonPcStyle,
  isEnabled: Boolean,
) {
  Theme(isDarkTheme) {
    ButtonPc(
      text = "Click",
      style = style,
      isEnabled = isEnabled,
    )
  }
}

@Composable
@Preview
private fun TextEnabledLightPreview() {
  ButtonPcThemed(
    isDarkTheme = false,
    style = ButtonPcStyle.TEXT,
    isEnabled = true,
  )
}

@Composable
@Preview
private fun TextDisabledLightPreview() {
  ButtonPcThemed(
    isDarkTheme = false,
    style = ButtonPcStyle.TEXT,
    isEnabled = false,
  )
}

@Composable
@Preview
private fun TextEnabledDarkPreview() {
  ButtonPcThemed(
    isDarkTheme = true,
    style = ButtonPcStyle.TEXT,
    isEnabled = true,
  )
}

@Composable
@Preview
private fun TextDisabledDarkPreview() {
  ButtonPcThemed(
    isDarkTheme = true,
    style = ButtonPcStyle.TEXT,
    isEnabled = false,
  )
}

@Composable
@Preview
private fun PrimaryEnabledLightPreview() {
  ButtonPcThemed(
    isDarkTheme = false,
    style = ButtonPcStyle.PRIMARY,
    isEnabled = true,
  )
}

@Composable
@Preview
private fun PrimaryDisabledLightPreview() {
  ButtonPcThemed(
    isDarkTheme = false,
    style = ButtonPcStyle.PRIMARY,
    isEnabled = false,
  )
}

@Composable
@Preview
private fun PrimaryEnabledDarkPreview() {
  ButtonPcThemed(
    isDarkTheme = true,
    style = ButtonPcStyle.PRIMARY,
    isEnabled = true,
  )
}

@Composable
@Preview
private fun PrimaryDisabledDarkPreview() {
  ButtonPcThemed(
    isDarkTheme = true,
    style = ButtonPcStyle.PRIMARY,
    isEnabled = false,
  )
}

@Composable
@Preview
private fun SecondaryEnabledLightPreview() {
  ButtonPcThemed(
    isDarkTheme = false,
    style = ButtonPcStyle.SECONDARY,
    isEnabled = true,
  )
}

@Composable
@Preview
private fun SecondaryDisabledLightPreview() {
  ButtonPcThemed(
    isDarkTheme = false,
    style = ButtonPcStyle.SECONDARY,
    isEnabled = false,
  )
}

@Composable
@Preview
private fun SecondaryEnabledDarkPreview() {
  ButtonPcThemed(
    isDarkTheme = true,
    style = ButtonPcStyle.SECONDARY,
    isEnabled = true,
  )
}

@Composable
@Preview
private fun SecondaryDisabledDarkPreview() {
  ButtonPcThemed(
    isDarkTheme = true,
    style = ButtonPcStyle.SECONDARY,
    isEnabled = false,
  )
}

@Composable
@Preview
private fun DestructivePrimaryEnabledLightPreview() {
  ButtonPcThemed(
    isDarkTheme = false,
    style = ButtonPcStyle.DESTRUCTIVE_PRIMARY,
    isEnabled = true,
  )
}

@Composable
@Preview
private fun DestructivePrimaryDisabledLightPreview() {
  ButtonPcThemed(
    isDarkTheme = false,
    style = ButtonPcStyle.DESTRUCTIVE_PRIMARY,
    isEnabled = false,
  )
}

@Composable
@Preview
private fun DestructivePrimaryEnabledDarkPreview() {
  ButtonPcThemed(
    isDarkTheme = true,
    style = ButtonPcStyle.DESTRUCTIVE_PRIMARY,
    isEnabled = true,
  )
}

@Composable
@Preview
private fun DestructivePrimaryDisabledDarkPreview() {
  ButtonPcThemed(
    isDarkTheme = true,
    style = ButtonPcStyle.DESTRUCTIVE_PRIMARY,
    isEnabled = false,
  )
}

@Composable
@Preview
private fun DestructiveSecondaryEnabledLightPreview() {
  ButtonPcThemed(
    isDarkTheme = false,
    style = ButtonPcStyle.DESTRUCTIVE_SECONDARY,
    isEnabled = true,
  )
}

@Composable
@Preview
private fun DestructiveSecondaryDisabledLightPreview() {
  ButtonPcThemed(
    isDarkTheme = false,
    style = ButtonPcStyle.DESTRUCTIVE_SECONDARY,
    isEnabled = false,
  )
}

@Composable
@Preview
private fun DestructiveSecondaryEnabledDarkPreview() {
  ButtonPcThemed(
    isDarkTheme = true,
    style = ButtonPcStyle.DESTRUCTIVE_SECONDARY,
    isEnabled = true,
  )
}

@Composable
@Preview
private fun DestructiveSecondaryDisabledDarkPreview() {
  ButtonPcThemed(
    isDarkTheme = true,
    style = ButtonPcStyle.DESTRUCTIVE_SECONDARY,
    isEnabled = false,
  )
}
