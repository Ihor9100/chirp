package com.plcoding.core.designsystem.style

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ColorToken {
  Primary,
  Secondary,
  OnPrimary,
  OnSecondary,

  TextPrimary,
  TextSecondary,
  TextTertiary,

  Background,
  Surface,

  Error,
}

@Composable
fun ColorToken.getColor(): Color {
  return when (this) {
    ColorToken.Primary -> MaterialTheme.colorScheme.primary
    ColorToken.Secondary -> MaterialTheme.colorScheme.secondary
    ColorToken.OnPrimary -> MaterialTheme.colorScheme.onPrimary
    ColorToken.OnSecondary -> MaterialTheme.colorScheme.onSecondary
    ColorToken.TextPrimary -> MaterialTheme.colorScheme.extended.textPrimary
    ColorToken.TextSecondary -> MaterialTheme.colorScheme.extended.textSecondary
    ColorToken.TextTertiary -> MaterialTheme.colorScheme.extended.textTertiary
    ColorToken.Background -> MaterialTheme.colorScheme.background
    ColorToken.Surface -> MaterialTheme.colorScheme.surface
    ColorToken.Error -> MaterialTheme.colorScheme.error
  }
}
