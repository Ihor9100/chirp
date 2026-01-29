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
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.utils.DeviceConfiguration
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BaseScreenOverlaysContent(
  modifier: Modifier = Modifier,
  baseContent: BaseContent,
  content: @Composable () -> Unit,
) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    content()

    baseContent.overlays?.forEach { overlay ->
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
              .size(24.dp)
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
                    .padding(16.dp)
                } else {
                  Modifier
                }
              ),
            color = MaterialTheme.colorScheme.primary,
          )
        }
        is Overlay.Snackbar -> {
          val snackbarHostState = remember { SnackbarHostState() }

          LaunchedEffect(overlay.event) {
            overlay.event.consumeAsync { snackbarHostState.showSnackbar(getString(it)) }
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
    BaseScreenOverlaysContent(
      modifier = Modifier.fillMaxSize(),
      baseContent = BaseContent.mock,
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
