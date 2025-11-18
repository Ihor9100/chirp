package com.plcoding.core.designsystem.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.ChirTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirFloatingActionButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  FloatingActionButton(
    onClick = onClick,
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.onPrimary,
    content = content,
  )
}

@Composable
@Preview
fun ChirFloatingActionButtonPreview() {
  ChirTheme {
    ChirFloatingActionButton(
      onClick = {},
      content = {
        Image(
          imageVector = Icons.Default.Add,
          contentDescription = null,
        )
      }
    )
  }
}