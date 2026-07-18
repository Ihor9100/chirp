package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.delete_for_everyone
import chirp.feature.chat.presentation.generated.resources.ic_reload
import com.plcoding.core.designsystem.components.DropDownMenu
import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LocalMessage(
  modifier: Modifier,
  localMessageUi: ChatMessageUi.LocalMessageUi,
  longPressedMessageId: String?,
  onLongClick: (messageId: String) -> Unit,
  onMenuClick: (DropDownItemUi) -> Unit,
  onMenuDismiss: () -> Unit,
  onRetry: (messageId: String) -> Unit,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .combinedClickable(
        onClick = {},
        onLongClick = { onLongClick(localMessageUi.id) },
      ),
    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
    verticalAlignment = Alignment.Bottom,
  ) {
    Box(
      modifier = Modifier.weight(1f, fill = false),
    ) {
      ChatBox(
        chatBoxUi = localMessageUi.chatBoxUi,
      ) {
        localMessageUi.chatMessageStatusUi?.let {
          Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            Icon(
              modifier = Modifier.size(12.dp),
              imageVector = vectorResource(it.iconRes),
              contentDescription = null,
              tint = it.colorToken.toColor()
            )
            Text(
              text = stringResource(it.titleRes),
              style = MaterialTheme.typography.labelSmall,
              color = it.colorToken.toColor(),
            )
          }
        }
        if (localMessageUi.id == longPressedMessageId) {
          val dropDownItemUi = DropDownItemUi(
            id = localMessageUi.id,
            leadingIconRes = null,
            titleRes = Res.string.delete_for_everyone,
            colorToken = ColorToken.TextDestructive,
          )
          DropDownMenu(
            modifier = Modifier,
            showMenu = true,
            items = listOf(dropDownItemUi),
            onAction = { onMenuClick(dropDownItemUi) },
            onDismiss = onMenuDismiss,
          )
        }
      }
    }
    if (localMessageUi.showRetryIcon) {
      Icon(
        modifier = Modifier
          .size(24.dp)
          .clickable { onRetry(localMessageUi.id) },
        imageVector = vectorResource(Res.drawable.ic_reload),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.extended.textDestructive
      )
    }
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    LocalMessage(
      modifier = Modifier,
      localMessageUi = ChatMessageUi.LocalMessageUi.mock,
      longPressedMessageId = "",
      onLongClick = {},
      onMenuClick = {},
      onMenuDismiss = {},
      onRetry = {},
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
