package com.plcoding.feature.chat.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.img_chat
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import com.plcoding.core.designsystem.components.Avatar
import com.plcoding.core.designsystem.components.TitleDescription
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.designsystem.style.titleXSmall
import com.plcoding.feature.chat.presentation.model.ChatEmptyStateUi
import com.plcoding.feature.chat.presentation.model.ChatMemberUi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatEmptyState(
  modifier: Modifier,
  chatEmptyStatePm: ChatEmptyStateUi,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Image(
      painter = painterResource(Res.drawable.img_chat),
      contentDescription = null,
    )
    TitleDescription(
      titleRes = chatEmptyStatePm.titleRes,
      descriptionRes = chatEmptyStatePm.descriptionRes,
    )
  }
}

@Composable
private fun Themed(
  isDarkTheme: Boolean = false,
) {
  Theme(isDarkTheme) {
    ChatEmptyState(
      modifier = Modifier,
      chatEmptyStatePm = ChatEmptyStateUi.mock,
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
