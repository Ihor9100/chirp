package com.plcoding.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.style.extended

data class AvatarPm(
  val fullName: String,
  val imageUrl: String?
) {

  fun getInitials(): String {
    if (fullName.isBlank()) return "?"

    return fullName
      .split(" ")
      .take(2)
      .joinToString(
        separator = "",
        transform = { it.first().uppercase() },
      )
  }
}

@Composable
fun Avatar(
  modifier: Modifier = Modifier,
  avatarPm: AvatarPm,
) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = avatarPm.getInitials(),
      color = MaterialTheme.colorScheme.extended.textPlaceholder,
      style = MaterialTheme.typography.titleMedium,
    )
  }
}
