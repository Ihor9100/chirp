package com.plcoding.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.model.AnchorPositionPm
import com.plcoding.core.designsystem.model.ChatMessagePm
import com.plcoding.core.designsystem.shape.ChatMessageShape
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatMessagePc(
  modifier: Modifier = Modifier,
  chatMessagePm: ChatMessagePm,
) {
  val horizontalPadding = 16.dp
  val verticalPadding = 12.dp
  val startPadding: Dp
  val endPadding: Dp

  when (chatMessagePm.anchorPositionPm) {
    AnchorPositionPm.LEFT -> {
      startPadding = horizontalPadding * 2
      endPadding = horizontalPadding
    }
    AnchorPositionPm.RIGHT -> {
      startPadding = horizontalPadding
      endPadding = horizontalPadding * 2
    }
  }

  Column(
    modifier = modifier
      .background(
        color = MaterialTheme.colorScheme.surface,
        shape = ChatMessageShape(chatMessagePm.anchorPositionPm, horizontalPadding)
      )
      .padding(
        start = startPadding,
        end = endPadding,
        top = verticalPadding,
        bottom = verticalPadding,
      ),
    verticalArrangement = Arrangement.spacedBy(6.dp),
    horizontalAlignment = Alignment.Start,
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = chatMessagePm.sender,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.extended.textSecondary
      )
      Text(
        text = chatMessagePm.date,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.extended.textSecondary
      )
    }
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = chatMessagePm.message,
      style = MaterialTheme.typography.bodyLarge,
      color = MaterialTheme.colorScheme.extended.textPrimary
    )
    chatMessagePm.chatSendingStatusPm?.let {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Icon(
          modifier = modifier.size(12.dp),
          imageVector = it.icon,
          contentDescription = null,
          tint = it.color
        )
        Text(
          text = stringResource(it.titleRes),
          style = MaterialTheme.typography.labelSmall,
          color = it.color,
        )
      }
    }
  }
}

@Composable
@Preview
private fun Themed(
  isDarkMode: Boolean,
  chatMessagePm: ChatMessagePm
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    ChatMessagePc(
      modifier = Modifier,
      chatMessagePm = chatMessagePm,
    )
  }
}

@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkMode = true,
    chatMessagePm = ChatMessagePm.mocks[0]
  )
}

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkMode = false,
    chatMessagePm = ChatMessagePm.mocks[1]
  )
}
