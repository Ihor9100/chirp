package com.plcoding.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.style.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StackedAvatars(
  modifier: Modifier = Modifier,
  avatarsPm: List<AvatarPm>,
  maxVisibleCount: Int = 2,
  horizontalOffset: Float = 0.4f,
) {
  val invisibleCount = avatarsPm.size - maxVisibleCount
  val horizontalOffsetDp = avatarsPm.firstOrNull()
    ?.avatarSize?.dp
    ?.times(horizontalOffset)
    ?: 0.dp

  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(-horizontalOffsetDp)
  ) {
    avatarsPm
      .take(maxVisibleCount)
      .forEach {
        Avatar(
          modifier = Modifier,
          avatarPm = it
        )
      }

    if (invisibleCount > 0) {
      Avatar(
        modifier = Modifier,
        AvatarPm(
          fullName = "$invisibleCount+",
          imageUrl = null,
          avatarSize = AvatarSize.MEDIUM,
        )
      )
    }
  }
}

@Composable
private fun StackedAvatarsThemed(
  isDarkMode: Boolean,
  avatarPm: AvatarPm,
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    StackedAvatars(
      modifier = Modifier,
      avatarPm = avatarPm,
    )
  }
}

@Composable
@Preview
private fun DarkPreview() {
  StackedAvatarsThemed(
    isDarkMode = true,
    avatarPm = AvatarPm(),
  )
}

@Composable
@Preview
private fun LightPreview() {
  StackedAvatarsThemed(
    isDarkMode = false,
    avatarPm = AvatarPm(),
  )
}
