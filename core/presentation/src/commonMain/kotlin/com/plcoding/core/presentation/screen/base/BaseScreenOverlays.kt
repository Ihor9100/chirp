package com.plcoding.core.presentation.screen.base

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.model.BaseContentPm
import com.plcoding.core.presentation.screen.model.Overlay
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BaseScreenOverlays(
  modifier: Modifier = Modifier,
  baseContentPm: BaseContentPm,
  content: @Composable () -> Unit,
) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    content()

    baseContentPm.overlays?.forEach { overlay ->
      when (overlay) {
        is Overlay.Blocker -> {
          Box(
            modifier = Modifier
              .matchParentSize()
              .background(Color.Black.copy(alpha = 0.2f))
              .pointerInput(Unit) {}
          )
        }
        is Overlay.Loader -> {
          CircularProgressIndicator(
            modifier = Modifier
              .then(
                if (overlay.showBackground) {
                  Modifier
                    .background(
                      color = MaterialTheme.colorScheme.primary,
                      shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                      width = 1.dp,
                      shape = RoundedCornerShape(8.dp),
                      color = MaterialTheme.colorScheme.outline,
                    )
                    .padding(24.dp)
                    .size(24.dp)
                } else {
                  Modifier
                }
              ),
            color = MaterialTheme.colorScheme.onPrimary,
            strokeWidth = 3.dp,
          )
        }
        is Overlay.Snackbar -> {
          val snackbarHostState = remember { SnackbarHostState() }

          LaunchedEffect(overlay.event) {
            overlay.event.consumeAsync { snackbarHostState.showSnackbar(getString(it)) }
            overlay.onDismiss?.invoke()
          }

          SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
          )
        }
      }
    }
  }
}


@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  Theme(isDarkTheme) {
    BaseScreenOverlays(
      modifier = Modifier.fillMaxSize(),
      baseContentPm = BaseContentPm.mock,
      content = { },
    )
  }
}

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkTheme = false,
  )
}

@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkTheme = true,
  )
}
