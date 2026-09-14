package com.plcoding.core.designsystem.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.plcoding.core.designsystem.utils.testTagOrSame

@Composable
fun Error(
  error: String,
  testTag: String? = null,
) {
  Text(
    modifier = Modifier.testTagOrSame(testTag),
    text = error,
    color = MaterialTheme.colorScheme.error,
    textAlign = TextAlign.Center,
    style = MaterialTheme.typography.labelSmall,
  )
}