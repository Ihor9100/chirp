package com.plcoding.core.designsystem.components.brand

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChirError(
  modifier: Modifier = Modifier,
  error: String,
) {
  Text(
    text = error,
    modifier = modifier,
    color = MaterialTheme.colorScheme.error,
    style = MaterialTheme.typography.labelSmall,
  )
}