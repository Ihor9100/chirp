package com.plcoding.core.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.style.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HorizontalDivider(
  modifier: Modifier = Modifier
) {
  HorizontalDivider(
    modifier = modifier.fillMaxWidth(),
    color = MaterialTheme.colorScheme.outline
  )
}

@Composable
@Preview
fun Preview() {
  Theme {
    HorizontalDivider()
  }
}