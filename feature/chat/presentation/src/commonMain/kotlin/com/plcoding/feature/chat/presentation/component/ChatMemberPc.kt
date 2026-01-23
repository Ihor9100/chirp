package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.Avatar
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.style.titleXSmall
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatMemberPc(
  modifier: Modifier,
  chatMemberPm: ChatMemberPm,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(MaterialTheme.colorScheme.surface),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Avatar(
      avatarPm = chatMemberPm.avatarPm
    )
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = chatMemberPm.fullName,
      color = MaterialTheme.colorScheme.extended.textPrimary,
      style = MaterialTheme.typography.titleXSmall,
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    ChatMemberPc(
      modifier = Modifier,
      chatMemberPm = ChatMemberPm.mock,
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
