package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.core.presentation.screen.model.BaseContentPm
import com.plcoding.core.presentation.utils.clearFocusOnTab

@Composable
fun BaseScreen(
  modifier: Modifier = Modifier,
  baseContentPm: BaseContentPm,
  content: @Composable () -> Unit,
) {
  BaseScreenOverlays(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .clearFocusOnTab()
      .safeDrawingPadding(),
    baseContentPm = baseContentPm,
    content = content,
  )
}
