package com.plcoding.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.MarkChatRead
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.failed
import chirp.core.designsystem.generated.resources.sent
import com.plcoding.core.designsystem.shape.AnchorPosition
import com.plcoding.core.designsystem.shape.ChatMessageShape
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
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
  Column(
    modifier = modifier
      .clip(ChatMessageShape(sender.getAnchorPosition()))
      .padding(horizontal = 16.dp)
      .background(backgroundColor)
      .padding(horizontal = 14.dp, vertical = 12.dp),
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Icon(
          modifier = modifier.size(16.dp),
          imageVector = it.icon,
          contentDescription = null
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
      backgroundColor = MaterialTheme.colorScheme.extended.accentGrey
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
    isDarkMode = true,
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
    isDarkMode = true,
    sender = Sender.Other("Bohdana"),
    status = Status.error
  )
}

sealed interface Sender {

  val name: String

  data class You(override val name: String) : Sender
  data class Other(override val name: String) : Sender

  fun getAnchorPosition() = when (this) {
    is You -> AnchorPosition.RIGHT
    is Other -> AnchorPosition.LEFT
  }
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
        icon = Icons.Default.MarkChatRead,
        titleRes = Res.string.sent,
        color = MaterialTheme.colorScheme.extended.textPrimary,
      )

    val error: Status
      @Composable
      get() = Status(
        icon = Icons.Default.Error,
        titleRes = Res.string.failed,
        color = MaterialTheme.colorScheme.error,
      )
  }
}
