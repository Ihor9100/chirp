package com.plcoding.feature.chat.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.components.Avatar
import com.plcoding.core.designsystem.components.StackedAvatars
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.style.titleXSmall
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import com.plcoding.feature.chat.presentation.model.ChatPm

@Composable
fun ChatMember(
  modifier: Modifier,
  chatPm: ChatPm,
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      StackedAvatars(
        avatarsPm = chatPm.avatarsPm,
      )
      Column {
        Text(
          text = chatPm.title,
          color = MaterialTheme.colorScheme.extended.textPrimary,
          style = MaterialTheme.typography.titleXSmall,
        )
        chatPm.description?.let {
          Text(
            text = it,
            color = MaterialTheme.colorScheme.extended.textPlaceholder,
            style = MaterialTheme.typography.bodySmall,
          )
        }
      }
    }
    Text(
      text = chatPm.content,
      color = MaterialTheme.colorScheme.extended.textSecondary,
      style = MaterialTheme.typography.bodySmall,
    )
  }
}
