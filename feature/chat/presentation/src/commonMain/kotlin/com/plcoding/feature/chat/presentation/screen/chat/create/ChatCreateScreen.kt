package com.plcoding.feature.chat.presentation.screen.chat.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.create_chat
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.designsystem.style.extended
import com.plcoding.core.presentation.screen.base.BaseScreenContent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatCreateScreen(
  navController: NavController,
  viewModel: ChatCreateScreenViewModel = viewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  BaseScreenContent(
    baseContent = state.baseContent,
    backgroundColor = MaterialTheme.colorScheme.extended.surfaceLower,
  ) {
    ChatCreateScreenContent(
      content = state.content,
      onAction = viewModel::onAction
    )
  }
}

@Composable
fun ChatCreateScreenContent(
  content: ChatCreateScreenContent,
  onAction: (ChatCreateScreenAction) -> Unit,
) {
  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(
          horizontal = 16.dp,
          vertical = 12.dp,
        ),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = stringResource(Res.string.create_chat),
        color = MaterialTheme.colorScheme.extended.textPrimary,
        style = MaterialTheme.typography.titleMedium,
      )
      IconButton(
        onClick = { onAction(ChatCreateScreenAction.OnDismiss }
      ) {
        Icon(
          imageVector = Icons.Default.Close
        )
      }
    }
  }
}

@Preview
@Composable
private fun ChatCreateScreenPreview() {
  Theme {
    ChatCreateScreenContent(
      content = ChatCreateScreenContent(),
      onAction = {}
    )
  }
}