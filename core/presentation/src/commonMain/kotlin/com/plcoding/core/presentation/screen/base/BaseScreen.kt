package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.core.presentation.model.BaseUiState
import com.plcoding.core.presentation.utils.clearFocusOnTab

@Composable
fun BaseScreen(
  modifier: Modifier = Modifier,
  baseUiState: BaseUiState,
  content: @Composable () -> Unit,
) {
  BaseScreenOverlays(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .clearFocusOnTab()
      .safeDrawingPadding(),
    baseUiState = baseUiState,
    content = content,
  )
}
