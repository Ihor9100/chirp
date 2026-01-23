package com.plcoding.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class AvatarSize(val dp: Dp) {
  MEDIUM(40.dp),
  LARGE(64.dp),
}

data class AvatarPm(
  val initials: String,
  val imageUrl: String?,
  val avatarSize: AvatarSize,
)

@Composable
fun Avatar(
  modifier: Modifier = Modifier,
  avatarPm: AvatarPm,
) {
  Box(
    modifier = modifier
      .size(avatarPm.avatarSize.dp)
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
private fun AvatarThemed(
  isDarkMode: Boolean,
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    Avatar(
      modifier = Modifier,
      avatarPm = AvatarPm(
        initials = "IB",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a3/June_odd-eyed-cat.jpg",
        avatarSize = AvatarSize.MEDIUM,
      ),
    )
  }
}

@Composable
@Preview
private fun DarkPreview() {
  AvatarThemed(
    isDarkMode = true,
  )
}

@Composable
@Preview
private fun LightPreview() {
  AvatarThemed(
    isDarkMode = false,
  )
}
