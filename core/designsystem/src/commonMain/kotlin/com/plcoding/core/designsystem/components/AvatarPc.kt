package com.plcoding.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AvatarPc(
  modifier: Modifier = Modifier,
  avatarPm: AvatarPm,
  onClick: (() -> Unit)? = null,
) {
  Box(
    modifier = modifier
      .size(avatarPm.avatarSizePm.dp)
      .clickable { onClick?.invoke() }
      .background(
        color = MaterialTheme.colorScheme.extended.secondaryFill,
        shape = CircleShape,
      )
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.outline,
        shape = CircleShape,
      ),
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = avatarPm.initials,
      color = MaterialTheme.colorScheme.extended.textPlaceholder,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.titleMedium,
    )
    AsyncImage(
      modifier = Modifier
        .clip(CircleShape),
      model = avatarPm.imageUrl,
      contentDescription = null,
      contentScale = ContentScale.Crop,
    )
  }
}

@Composable
private fun Themed(
  isDarkMode: Boolean,
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    AvatarPc(
      modifier = Modifier,
      avatarPm = AvatarPm.mock,
    )
  }
}

@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkMode = true,
  )
}

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkMode = false,
  )
}
