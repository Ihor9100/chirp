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
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Avatar(
  modifier: Modifier = Modifier,
  avatarUi: AvatarUi,
  onClick: (() -> Unit)? = null,
) {
  Box(
    modifier = modifier
      .size(avatarUi.avatarSizeUi.dp)
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
      text = avatarUi.initials,
      color = MaterialTheme.colorScheme.extended.textPlaceholder,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.titleMedium,
    )
    AsyncImage(
      modifier = Modifier.clip(CircleShape),
      model = avatarUi.imageUrl,
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
    Avatar(
      modifier = Modifier,
      avatarUi = AvatarUi.mocks[0],
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
