package com.plcoding.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.designsystem.model.AvatarSizeUi
import com.plcoding.core.designsystem.style.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StackedAvatars(
  modifier: Modifier = Modifier,
  avatarsUi: List<AvatarUi>,
  maxVisibleCount: Int = 2,
  horizontalOffset: Float = 0.4f,
) {
  val invisibleCount = avatarsUi.size - maxVisibleCount
  val horizontalOffsetDp = avatarsUi.firstOrNull()
    ?.avatarSizeUi?.dp
    ?.times(horizontalOffset)
    ?: 0.dp

  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(horizontalOffsetDp.unaryMinus())
  ) {
    avatarsUi
      .take(maxVisibleCount)
      .forEach {
        Avatar(
          modifier = Modifier,
          avatarUi = it
        )
      }

    if (invisibleCount > 0) {
      Avatar(
        modifier = Modifier,
        avatarUi = AvatarUi(
          initials = "$invisibleCount +",
          imageUrl = null,
          avatarSizeUi = AvatarSizeUi.MEDIUM,
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
      avatarsUi = listOf(
        AvatarUi(
          initials = "Ihor A",
          imageUrl = "1",
          avatarSizeUi = AvatarSizeUi.MEDIUM,
        ),
        AvatarUi(
          initials = "Ihor B",
          imageUrl = "2",
          avatarSizeUi = AvatarSizeUi.MEDIUM,
        ),
        AvatarUi(
          initials = "Ihor C",
          imageUrl = "3",
          avatarSizeUi = AvatarSizeUi.MEDIUM,
        ),
        AvatarUi(
          initials = "Ihor D",
          imageUrl = "4",
          avatarSizeUi = AvatarSizeUi.MEDIUM,
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
