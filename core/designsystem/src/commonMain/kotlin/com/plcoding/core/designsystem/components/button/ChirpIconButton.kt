package com.plcoding.core.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.ChirTheme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpIconButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  OutlinedIconButton(
    onClick = onClick,
    modifier = modifier
      .size(45.dp),
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(
      width = 1.dp,
      color = MaterialTheme.colorScheme.outline,
    ),
    colors = IconButtonDefaults.outlinedIconButtonColors(
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = MaterialTheme.colorScheme.extended.textSecondary,
    ),
    content = content,
  )
}

@Composable
fun ChirpIconButtonThemed(
  isDarkTheme: Boolean,
) {
  ChirTheme(isDarkTheme) {
    ChirpIconButton(
      onClick = {}
    ) {
      Icon(
        imageVector = Icons.Default.Air,
        contentDescription = null,
      )
    }
  }
}

@Composable
@Preview
fun ChirpIconButtonLightPreview() {
  ChirpIconButtonThemed(
    isDarkTheme = false,
  )
}

@Composable
@Preview
fun ChirpIconButtonDarkPreview() {
  ChirpIconButtonThemed(
    isDarkTheme = true,
  )
}
