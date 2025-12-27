package com.plcoding.core.designsystem.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ChirError(
  error: String,
) {
  Text(
    text = error,
    color = MaterialTheme.colorScheme.error,
    textAlign = TextAlign.Center,
    style = MaterialTheme.typography.labelSmall,
  )
}