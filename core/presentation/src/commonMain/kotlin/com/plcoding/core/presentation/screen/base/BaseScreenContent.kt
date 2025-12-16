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
fun BaseScreenContent(
  modifier: Modifier = Modifier,
  baseContent: BaseContent,
  content: @Composable BoxScope.() -> Unit,
) {
  Box(
    modifier = modifier.fillMaxSize()
  ) {
    content()

    when (baseContent.overlay) {
      Overlay.NONE -> Unit
      Overlay.BLOCKABLE -> {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f))
            .pointerInput(Unit) {}
        )
      }
    }
  }
}
