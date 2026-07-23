package com.plcoding.core.designsystem.utils

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.clearFocusOnTab(): Modifier {
  val focusManager = LocalFocusManager.current
  return pointerInput(Unit) {
    detectTapGestures { focusManager.clearFocus() }
  }
}

@Composable
fun Modifier.windowInsetsPaddingOrMin(
  insets: WindowInsets,
  sides: WindowInsetsSides = WindowInsetsSides.Top +
    WindowInsetsSides.Bottom +
    WindowInsetsSides.Start +
    WindowInsetsSides.End,
  minTop: Dp = 0.dp,
  minBottom: Dp = 0.dp,
  minStart: Dp = 0.dp,
  minEnd: Dp = 0.dp,
): Modifier {
  val density = LocalDensity.current
  val layoutDirection = LocalLayoutDirection.current
  val filteredInsets = insets.only(sides)

  val top = maxOf(
    with(density) { filteredInsets.getTop(this).toDp() },
    minTop,
  )

  val bottom = maxOf(
    with(density) { filteredInsets.getBottom(this).toDp() },
    minBottom,
  )

  val start = maxOf(
    with(density) { filteredInsets.getLeft(this, layoutDirection).toDp() },
    minStart,
  )

  val end = maxOf(
    with(density) { filteredInsets.getRight(this, layoutDirection).toDp() },
    minEnd,
  )

  return padding(
    top = top,
    bottom = bottom,
    start = start,
    end = end,
  )
}