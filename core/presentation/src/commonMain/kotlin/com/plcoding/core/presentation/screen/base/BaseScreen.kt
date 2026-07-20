package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.plcoding.core.presentation.model.BaseUiState
import com.plcoding.core.presentation.utils.clearFocusOnTab

@Composable
fun BaseScreen(
  modifier: Modifier = Modifier,
  baseUiState: BaseUiState,
  backgroundColor: Color?,
  isSafeDrawing: Boolean = true,
  content: @Composable () -> Unit,
) {
  BaseScreenOverlays(
    modifier = modifier
      .fillMaxSize()
      .then(backgroundColor?.let(Modifier::background) ?: Modifier)
      .clearFocusOnTab()
      .then(if (isSafeDrawing) Modifier.safeDrawingPadding() else Modifier),
    baseUiState = baseUiState,
    content = content,
  )
}
