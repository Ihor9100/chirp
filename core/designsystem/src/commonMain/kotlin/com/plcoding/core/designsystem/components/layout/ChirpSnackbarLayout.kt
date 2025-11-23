package com.plcoding.core.designsystem.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpSnackbarLayout(
  modifier: Modifier = Modifier,
  snackbarHostState: SnackbarHostState,
  content: @Composable () -> Unit,
) {
  Scaffold(
    modifier = modifier,
    snackbarHost = {
      SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp)
      )
    },
    contentWindowInsets = WindowInsets.statusBars
      .union(WindowInsets.displayCutout)
      .union(WindowInsets.ime)
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .padding(innerPadding)
        .fillMaxWidth(),
      contentAlignment = Alignment.TopCenter
    ) {
      content()
    }
  }
}

@Composable
fun ChirpSnackbarLayoutThemed(
  isDarkTheme: Boolean,
) {
  ChirpTheme(isDarkTheme) {
    val snackbarHostState = remember { SnackbarHostState() }

    ChirpSnackbarLayout(
      snackbarHostState = snackbarHostState,
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
fun ChirpSnackbarLayoutLightPreview() {
  ChirpSnackbarLayoutThemed(isDarkTheme = false)
}

@Composable
@Preview
fun ChirpSnackbarLayoutDarkPreview() {
  ChirpSnackbarLayoutThemed(isDarkTheme = true)
}
