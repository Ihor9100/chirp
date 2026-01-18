package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.plcoding.core.presentation.utils.clearFocusOnTab
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString

@Composable
fun BaseScreenContent(
  modifier: Modifier = Modifier,
  baseContent: BaseContent,
  content: @Composable BoxScope.() -> Unit,
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .clearFocusOnTab()
      .safeDrawingPadding(),
    contentAlignment = Alignment.Center,
  ) {
    content()

    baseContent.overlays?.forEach { overlay ->
      when (overlay) {
        Overlay.Blocker -> {
          Box(
            modifier = Modifier
              .matchParentSize()
              .background(Color.Black.copy(alpha = 0.2f))
              .pointerInput(Unit) {}
          )
        }
        Overlay.Loader -> {
          CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary,
          )
        }
        is Overlay.Snackbar -> {
          val snackbarHostState = remember { SnackbarHostState() }

          rememberCoroutineScope().launch {
            overlay.event.consumeAsync {
              snackbarHostState.showSnackbar(getString(it))
            }
          }

          SnackbarHost(snackbarHostState)
        }
      }
    }
  }
}
