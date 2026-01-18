package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.core.presentation.utils.clearFocusOnTab

@Composable
fun BaseScreenContent(
  modifier: Modifier = Modifier,
  baseContent: BaseContent,
  content: @Composable () -> Unit,
) {
  BaseScreenOverlaysContent(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .clearFocusOnTab()
      .safeDrawingPadding(),
    baseContent = baseContent,
    content = content,
  )
}
