package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.ic_cross
import com.plcoding.core.designsystem.components.ChatMessagePc
import com.plcoding.core.designsystem.components.HorizontalDividerPc
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.feature.chat.presentation.model.DateDividerPm
import com.plcoding.feature.chat.presentation.model.LocalMessagePm
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LocalMessagePc(
  modifier: Modifier,
  localMessagePm: LocalMessagePm,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    verticalAlignment = Alignment.Bottom,
  ) {
    ChatMessagePc(
      modifier = modifier,
      chatMessagePm = localMessagePm.chatMessagePm,
    )
    Icon(
      // TODO:
      imageVector = vectorResource(Res.drawable.ic_cross),
      contentDescription = null,
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    LocalMessagePc(
      modifier = Modifier,
      localMessagePm = LocalMessagePm.mock,
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
