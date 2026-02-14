package com.plcoding.core.designsystem.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.ic_plus
import com.plcoding.core.designsystem.style.Theme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FloatingButtonPc(
  modifier: Modifier = Modifier,
  iconRes: DrawableResource,
  onClick: () -> Unit,
) {
  FloatingActionButton(
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.onPrimary,
    onClick = onClick,
  ) {
    Image(
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
    FloatingButtonPc(
      iconRes = Res.drawable.ic_plus,
      onClick = {}
    )
  }
}

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkTheme = false
  )
}

@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkTheme = true
  )
}