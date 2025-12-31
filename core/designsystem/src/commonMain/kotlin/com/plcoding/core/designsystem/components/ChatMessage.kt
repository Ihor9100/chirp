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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.failed
import chirp.core.designsystem.generated.resources.ic_check
import chirp.core.designsystem.generated.resources.ic_cross
import chirp.core.designsystem.generated.resources.sent
import com.plcoding.core.designsystem.components.Sender.Other
import com.plcoding.core.designsystem.components.Sender.You
import com.plcoding.core.designsystem.shape.AnchorPosition
import com.plcoding.core.designsystem.shape.ChatMessageShape
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatMessage(
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
    is You -> {
      startPadding = horizontalPadding
      endPadding = horizontalPadding * 2
      AnchorPosition.RIGHT
    }
    is Other -> {
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
fun ChatMessageThemed(
  isDarkMode: Boolean,
  sender: Sender,
  status: Status,
) {
  Theme(
    isDarkMode = isDarkMode,
  ) {
    ChatMessage(
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
fun ChatMessageYouDarkPreview() {
  ChatMessageThemed(
    isDarkMode = true,
    sender = Sender.You("Ihor"),
    status = Status.success
  )
}

@Composable
@Preview
fun ChatMessageYouLightPreview() {
  ChatMessageThemed(
    isDarkMode = false,
    sender = Sender.You("Ihor"),
    status = Status.success
  )
}

@Composable
@Preview
fun ChatMessageOtherDarkPreview() {
  ChatMessageThemed(
    isDarkMode = true,
    sender = Sender.Other("Bohdana"),
    status = Status.error
  )
}

@Composable
@Preview
fun ChatMessageOtherLightPreview() {
  ChatMessageThemed(
    isDarkMode = false,
    sender = Sender.Other("Bohdana"),
    status = Status.error
  )
}

sealed interface Sender {

  val name: String

  data class You(override val name: String) : Sender
  data class Other(override val name: String) : Sender
}

@ConsistentCopyVisibility
data class Status private constructor(
  val icon: ImageVector,
  val titleRes: StringResource,
  val color: Color,
) {

  companion object {
    val success: Status
      @Composable
      get() = Status(
        icon = vectorResource(Res.drawable.ic_check),
        titleRes = Res.string.sent,
        color = MaterialTheme.colorScheme.extended.textTertiary,
      )

    val error: Status
      @Composable
      get() = Status(
        icon = vectorResource(Res.drawable.ic_cross),
        titleRes = Res.string.failed,
        color = MaterialTheme.colorScheme.error,
      )
  }
}
