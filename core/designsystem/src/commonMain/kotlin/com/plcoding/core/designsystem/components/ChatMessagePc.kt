package com.plcoding.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatMessagePc(
  modifier: Modifier = Modifier,
  chatMessagePm: ChatMessagePm,
  statusPc: @Composable (() -> Unit)? = null,
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
      .width(IntrinsicSize.Max)
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
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        modifier = Modifier.padding(end = 24.dp),
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
      modifier = Modifier,
      text = chatMessagePm.message,
      style = MaterialTheme.typography.bodyLarge,
      color = MaterialTheme.colorScheme.extended.textPrimary
    )
    statusPc?.invoke()
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
