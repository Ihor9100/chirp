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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.model.Sender
import com.plcoding.core.designsystem.model.Status
import com.plcoding.core.designsystem.shape.AnchorPosition
import com.plcoding.core.designsystem.shape.ChatMessageShape
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatMessagePc(
  modifier: Modifier = Modifier,
  sender: Sender,
  date: String,
  message: String,
  status: Status?,
  backgroundColor: Color,
) {
  val horizontalPadding = 16.dp
  val verticalPadding = 12.dp
  val startPadding: Dp
  val endPadding: Dp

  val anchorPosition = when (sender) {
    is Sender.You -> {
      startPadding = horizontalPadding
      endPadding = horizontalPadding * 2
      AnchorPosition.RIGHT
    }
    is Sender.Other -> {
      startPadding = horizontalPadding * 2
      endPadding = horizontalPadding
      AnchorPosition.LEFT
    }
  }

  Column(
    modifier = modifier
      .background(
        color = backgroundColor,
        shape = ChatMessageShape(anchorPosition, horizontalPadding)
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
        text = sender.name,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.extended.textSecondary
      )
      Text(
        text = date,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.extended.textSecondary
      )
    }
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = message,
      style = MaterialTheme.typography.bodyLarge,
      color = MaterialTheme.colorScheme.extended.textPrimary
    )
    status?.let {
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
  sender: Sender,
  status: Status,
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    ChatMessagePc(
      sender = sender,
      date = "today",
      message = "Hello World",
      status = status,
      backgroundColor = MaterialTheme.colorScheme.surface
    )
  }
}

@Composable
@Preview
private fun YouSuccessDarkPreview() {
  Themed(
    isDarkMode = true,
    sender = Sender.You("Ihor"),
    status = Status.success
  )
}

@Composable
@Preview
private fun YouErrorDarkPreview() {
  Themed(
    isDarkMode = true,
    sender = Sender.You("Ihor"),
    status = Status.error
  )
}

@Composable
@Preview
private fun OtherSuccessDarkPreview() {
  Themed(
    isDarkMode = true,
    sender = Sender.You("Bohdana"),
    status = Status.success
  )
}

@Composable
@Preview
private fun OtherErrorDarkPreview() {
  Themed(
    isDarkMode = true,
    sender = Sender.You("Bohdana"),
    status = Status.error
  )
}

@Composable
@Preview
private fun YouSuccessLightPreview() {
  Themed(
    isDarkMode = false,
    sender = Sender.You("Ihor"),
    status = Status.success
  )
}

@Composable
@Preview
private fun YouErrorLightPreview() {
  Themed(
    isDarkMode = false,
    sender = Sender.You("Ihor"),
    status = Status.error
  )
}

@Composable
@Preview
private fun OtherSuccessLightPreview() {
  Themed(
    isDarkMode = false,
    sender = Sender.You("Bohdana"),
    status = Status.success
  )
}

@Composable
@Preview
private fun OtherErrorLightPreview() {
  Themed(
    isDarkMode = false,
    sender = Sender.You("Bohdana"),
    status = Status.error
  )
}
