package com.plcoding.core.designsystem.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.ic_dots
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IconButtonPc(
  modifier: Modifier = Modifier,
  iconRes: DrawableResource,
  onClick: () -> Unit,
) {
  OutlinedIconButton(
    onClick = onClick,
    modifier = modifier.size(45.dp),
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(
      width = 1.dp,
      color = MaterialTheme.colorScheme.outline,
    ),
    colors = IconButtonDefaults.outlinedIconButtonColors(
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = MaterialTheme.colorScheme.extended.textSecondary,
    ),
  ) {
    Icon(
      imageVector = vectorResource(iconRes),
      contentDescription = null,
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  Theme(isDarkTheme) {
    IconButtonPc(
      iconRes = Res.drawable.ic_dots,
      onClick = {}
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
