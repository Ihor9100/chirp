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
  TextDestructive,

  Background,
  Surface,
  SurfaceLower,

  Error,

  CakeViolet,
  CakeGreen,
  CakePink,
  CakeOrange,
  CakeBlue,
  CakeYellow,
  CakePurple,
  CakeRed,
  CakeMint;

  companion object {
    fun get(id: String): ColorToken {
      val colorTokens = listOf(
        CakeViolet,
        CakeGreen,
        CakePink,
        CakeOrange,
        CakeBlue,
        CakeYellow,
        CakePurple,
        CakeRed,
        CakeMint,
      )
      val index = id.hashCode().toUInt() % colorTokens.size.toUInt()
      return colorTokens[index.toInt()]
    }
  }

  @Composable
  fun toColor(): Color {
    return when (this) {
      Primary -> MaterialTheme.colorScheme.primary
      Secondary -> MaterialTheme.colorScheme.secondary
      OnPrimary -> MaterialTheme.colorScheme.onPrimary
      OnSecondary -> MaterialTheme.colorScheme.onSecondary
      TextPrimary -> MaterialTheme.colorScheme.extended.textPrimary
      TextSecondary -> MaterialTheme.colorScheme.extended.textSecondary
      TextTertiary -> MaterialTheme.colorScheme.extended.textTertiary
      TextDestructive -> MaterialTheme.colorScheme.extended.textDestructive
      Background -> MaterialTheme.colorScheme.background
      Surface -> MaterialTheme.colorScheme.surface
      SurfaceLower -> MaterialTheme.colorScheme.extended.surfaceLower
      Error -> MaterialTheme.colorScheme.error
      CakeViolet -> MaterialTheme.colorScheme.extended.cakeViolet
      CakeGreen -> MaterialTheme.colorScheme.extended.cakeGreen
      CakePink -> MaterialTheme.colorScheme.extended.cakePink
      CakeOrange -> MaterialTheme.colorScheme.extended.cakeOrange
      CakeBlue -> MaterialTheme.colorScheme.extended.cakeBlue
      CakeYellow -> MaterialTheme.colorScheme.extended.cakeYellow
      CakePurple -> MaterialTheme.colorScheme.extended.cakePurple
      CakeRed -> MaterialTheme.colorScheme.extended.cakeRed
      CakeMint -> MaterialTheme.colorScheme.extended.cakeMint
    }
  }
}
