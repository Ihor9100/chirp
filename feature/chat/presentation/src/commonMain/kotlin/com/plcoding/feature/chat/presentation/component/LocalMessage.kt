package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
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
import chirp.feature.chat.presentation.generated.resources.ic_reload
import com.plcoding.core.designsystem.components.ChatBubble
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
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
    verticalAlignment = Alignment.Bottom,
  ) {
    ChatBubble(
      modifier = Modifier.weight(1f, fill = false),
      chatBubbleUi = localMessageUi.chatBubbleUi,
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
    }
    Icon(
      modifier = Modifier.size(24.dp),
      imageVector = vectorResource(Res.drawable.ic_reload),
      contentDescription = null,
      tint = MaterialTheme.colorScheme.extended.textDestructive
    )
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
