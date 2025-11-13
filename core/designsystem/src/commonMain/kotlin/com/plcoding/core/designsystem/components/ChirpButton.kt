package com.plcoding.core.designsystem.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class ChirpButtonStyle {
  TEXT,
  PRIMARY,
  SECONDARY,
  DESTRUCTIVE_PRIMARY,
  DESTRUCTIVE_SECONDARY,
}

@Composable
fun ChirpButton(
  text: String,
  modifier: Modifier = Modifier,
  style: ChirpButtonStyle = ChirpButtonStyle.PRIMARY,
  isLoading: Boolean = false,
  enabled: Boolean = true,
  onClick: () -> Unit = {},
  leadingIcon: @Composable (() -> Unit)? = null,
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = RoundedCornerShape(8.dp)
  ) {

  }
}
