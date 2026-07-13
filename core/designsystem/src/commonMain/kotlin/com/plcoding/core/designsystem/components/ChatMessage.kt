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
import com.plcoding.core.designsystem.model.AnchorPositionUi
import com.plcoding.core.designsystem.model.ChatMessageUi
import com.plcoding.core.designsystem.shape.ChatMessageShape
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.style.getColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatMessage(
  modifier: Modifier = Modifier,
  chatMessageUi: ChatMessageUi,
  status: @Composable (() -> Unit)? = null,
) {
  val horizontalPadding = 16.dp
  val verticalPadding = 12.dp
  val startPadding: Dp
  val endPadding: Dp

  when (chatMessageUi.anchorPositionUi) {
    AnchorPositionUi.LEFT -> {
      startPadding = horizontalPadding * 2
      endPadding = horizontalPadding
    }
    AnchorPositionUi.RIGHT -> {
      startPadding = horizontalPadding
      endPadding = horizontalPadding * 2
    }
  }

  Column(
    modifier = modifier
      .width(IntrinsicSize.Max)
      .background(
        color = chatMessageUi.colorToken.getColor(),
        shape = ChatMessageShape(chatMessageUi.anchorPositionUi, horizontalPadding)
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
        text = chatMessageUi.sender,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.extended.textSecondary
      )
      Text(
        text = chatMessageUi.date,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.extended.textSecondary
      )
    }
    Text(
      modifier = Modifier,
      text = chatMessageUi.message,
      style = MaterialTheme.typography.bodyLarge,
      color = MaterialTheme.colorScheme.extended.textPrimary
    )
    status?.invoke()
  }
}

@Composable
@Preview
private fun Themed(
  isDarkMode: Boolean,
  chatMessageUi: ChatMessageUi
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    ChatMessage(
      modifier = Modifier,
      chatMessageUi = chatMessageUi,
    )
  }
}

@Composable
@Preview
private fun DarkPreview() {
  Themed(
    isDarkMode = true,
    chatMessageUi = ChatMessageUi.mocks[0]
  )
}

@Composable
@Preview
private fun LightPreview() {
  Themed(
    isDarkMode = false,
    chatMessageUi = ChatMessageUi.mocks[1]
  )
}
