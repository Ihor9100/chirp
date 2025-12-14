package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun BaseScreen(
  modifier: Modifier = Modifier,
  baseScreenState: BaseScreenState<*>,
  content: @Composable BoxScope.() -> Unit,
) {
  Box(
    modifier = modifier.fillMaxSize()
  ) {
    content()

    if (baseScreenState.showLoader) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(Color.Black.copy(alpha = 0.2f))
          .pointerInput(Unit) {}
      )
    }
  }
}
