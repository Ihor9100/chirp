package com.plcoding.feature.chat.presentation.composable

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
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.style.titleXSmall
import com.plcoding.feature.chat.presentation.model.ChatMemberPm

@Composable
fun ChatMember(
  modifier: Modifier,
  chatMemberPm: ChatMemberPm,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Avatar(
      avatarPm = chatMemberPm.avatarPm
    )
    Text(
      text = chatMemberPm.fullName,
      color = MaterialTheme.colorScheme.extended.textPrimary,
      style = MaterialTheme.typography.titleXSmall,
    )
  }
}
