package com.plcoding.core.designsystem.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun Theme(
  isDarkMode: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (isDarkMode) DarkColorScheme else LightColorScheme
  val extendedColorScheme = if (isDarkMode) DarkExtendedColorScheme else LightExtendedColorScheme

  CompositionLocalProvider(LocalExtendedColorScheme provides extendedColorScheme) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content,
    )
  }
}

val LightColorScheme = lightColorScheme(
  primary = Brand500,
  onPrimary = Brand1000,
  primaryContainer = Brand100,
  onPrimaryContainer = Brand900,

  secondary = Base700,
  onSecondary = Base0,
  secondaryContainer = Base100,
  onSecondaryContainer = Base900,

  tertiary = Brand900,
  onTertiary = Base0,
  tertiaryContainer = Brand100,
  onTertiaryContainer = Brand1000,

  error = Red500,
  onError = Base0,
  errorContainer = Red200,
  onErrorContainer = Red600,

  background = Brand1000,
  onBackground = Base0,
  surface = Base0,
  onSurface = Base1000,
  surfaceVariant = Base100,
  onSurfaceVariant = Base900,

  outline = Base1000Alpha8,
  outlineVariant = Base200,
)

val LightExtendedColorScheme = ExtendedColorScheme(
  primaryHover = Brand600,
  destructiveHover = Red600,
  destructiveSecondaryOutline = Red200,
  disabledOutline = Base200,
  disabledFill = Base100,
  successOutline = Brand100,
  success = Brand600,
  onSuccess = Base0,
  secondaryFill = Base100,

  textPrimary = Base1000,
  textTertiary = Base800,
  textSecondary = Base900,
  textPlaceholder = Base700,
  textDisabled = Base400,
  textDestructive = Red600,

  surfaceLower = Base100,
  surfaceHigher = Base100,
  surfaceOutline = Base1000Alpha14,
  overlay = Base1000Alpha80,

  accentBlue = Blue,
  accentPurple = Purple,
  accentViolet = Violet,
  accentPink = Pink,
  accentOrange = Orange,
  accentYellow = Yellow,
  accentGreen = Green,
  accentTeal = Teal,
  accentLightBlue = LightBlue,
  accentGrey = Grey,

  cakeViolet = CakeLightViolet,
  cakeGreen = CakeLightGreen,
  cakeBlue = CakeLightBlue,
  cakePink = CakeLightPink,
  cakeOrange = CakeLightOrange,
  cakeYellow = CakeLightYellow,
  cakeTeal = CakeLightTeal,
  cakePurple = CakeLightPurple,
  cakeRed = CakeLightRed,
  cakeMint = CakeLightMint,
)

val DarkColorScheme = darkColorScheme(
  primary = Brand500,
  onPrimary = Brand1000,
  primaryContainer = Brand900,
  onPrimaryContainer = Brand500,

  secondary = Base400,
  onSecondary = Base1000,
  secondaryContainer = Base900,
  onSecondaryContainer = Base150,

  tertiary = Brand500,
  onTertiary = Base1000,
  tertiaryContainer = Brand900,
  onTertiaryContainer = Brand500,

  error = Red500,
  onError = Base0,
  errorContainer = Red600,
  onErrorContainer = Red200,

  background = Base1000,
  onBackground = Base0,
  surface = Base950,
  onSurface = Base0,
  surfaceVariant = Base900,
  onSurfaceVariant = Base150,

  outline = Base100Alpha10,
  outlineVariant = Base800,
)

val DarkExtendedColorScheme = ExtendedColorScheme(
  primaryHover = Brand600,
  destructiveHover = Red600,
  destructiveSecondaryOutline = Red200,
  disabledOutline = Base900,
  disabledFill = Base950,
  successOutline = Brand500Alpha40,
  success = Brand500,
  onSuccess = Base1000,
  secondaryFill = Base900,

  textPrimary = Base0,
  textTertiary = Base200,
  textSecondary = Base150,
  textPlaceholder = Base400,
  textDisabled = Base500,
  textDestructive = Red200,

  surfaceLower = Base1000,
  surfaceHigher = Base900,
  surfaceOutline = Base100Alpha10Alt,
  overlay = Base1000Alpha80,

  accentBlue = Blue,
  accentPurple = Purple,
  accentViolet = Violet,
  accentPink = Pink,
  accentOrange = Orange,
  accentYellow = Yellow,
  accentGreen = Green,
  accentTeal = Teal,
  accentLightBlue = LightBlue,
  accentGrey = Grey,

  cakeViolet = CakeDarkViolet,
  cakeGreen = CakeDarkGreen,
  cakeBlue = CakeDarkBlue,
  cakePink = CakeDarkPink,
  cakeOrange = CakeDarkOrange,
  cakeYellow = CakeDarkYellow,
  cakeTeal = CakeDarkTeal,
  cakePurple = CakeDarkPurple,
  cakeRed = CakeDarkRed,
  cakeMint = CakeDarkMint,
)
