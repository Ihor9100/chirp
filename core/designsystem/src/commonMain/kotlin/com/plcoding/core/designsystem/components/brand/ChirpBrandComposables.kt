package com.plcoding.core.designsystem.components.brand

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.ic_logo_chirp
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ChirBrandLogo(
  modifier: Modifier = Modifier,
) {
  Icon(
    imageVector = vectorResource(Res.drawable.ic_logo_chirp),
    contentDescription = null,
    modifier = modifier,
    tint = MaterialTheme.colorScheme.primary
  )
}

@Composable
fun ChirBrandTitle(
  modifier: Modifier = Modifier,
  title: String,
  titleColor: Color = MaterialTheme.colorScheme.extended.textPrimary,
  error: String? = null,
) {
  Text(
    text = title,
    modifier = modifier,
    color = titleColor,
    style = MaterialTheme.typography.headlineLarge,
  )

  Spacer(
    modifier = modifier.height(4.dp)
  )

  val showError = error != null

  AnimatedVisibility(
    visible = showError
  ) {
    if (showError) {
      Text(
        text = error,
        modifier = modifier,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelSmall,
      )
    }
  }
}