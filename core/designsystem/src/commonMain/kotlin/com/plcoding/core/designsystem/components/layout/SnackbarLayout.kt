package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SnackbarLayout(
  modifier: Modifier = Modifier,
  snackbarHostState: SnackbarHostState,
  content: @Composable () -> Unit,
) {
  Scaffold(
    modifier = modifier,
    snackbarHost = {
      SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
      )
    },
  ) { _ ->
    content()
  }
}

@Composable
fun SnackbarLayoutThemed(
  isDarkTheme: Boolean,
) {
  Theme(isDarkTheme) {
    SnackbarLayout(
      snackbarHostState = SnackbarHostState(),
    ) {
      Text(
        text = "Test message!!!",
        color = MaterialTheme.colorScheme.extended.textPrimary,
        style = MaterialTheme.typography.titleMedium,
      )
    }
  }
}

@Composable
@Preview
fun SnackbarLayoutLightPreview() {
  SnackbarLayoutThemed(isDarkTheme = false)
}

@Composable
@Preview
fun SnackbarLayoutDarkPreview() {
  SnackbarLayoutThemed(isDarkTheme = true)
}
