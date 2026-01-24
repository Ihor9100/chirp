package com.plcoding.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.model.AvatarPm
import com.plcoding.core.designsystem.model.AvatarSize
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
    horizontalArrangement = Arrangement.spacedBy(horizontalOffsetDp.unaryMinus())
  ) {
    avatarsPm
      .take(maxVisibleCount)
      .forEach {
        AvatarPc(
          modifier = Modifier,
          avatarPm = it
        )
      }

    if (invisibleCount > 0) {
      AvatarPc(
        modifier = Modifier,
        avatarPm = AvatarPm(
          initials = "$invisibleCount +",
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
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    StackedAvatars(
      modifier = Modifier,
      avatarsPm = listOf(
        AvatarPm(
          initials = "Ihor A",
          imageUrl = "1",
          avatarSize = AvatarSize.MEDIUM,
        ),
        AvatarPm(
          initials = "Ihor B",
          imageUrl = "2",
          avatarSize = AvatarSize.MEDIUM,
        ),
        AvatarPm(
          initials = "Ihor C",
          imageUrl = "3",
          avatarSize = AvatarSize.MEDIUM,
        ),
        AvatarPm(
          initials = "Ihor D",
          imageUrl = "4",
          avatarSize = AvatarSize.MEDIUM,
        ),
      ),
    )
  }
}

@Composable
@Preview
private fun DarkPreview() {
  StackedAvatarsThemed(
    isDarkMode = true,
  )
}

@Composable
@Preview
private fun LightPreview() {
  StackedAvatarsThemed(
    isDarkMode = false,
  )
}
