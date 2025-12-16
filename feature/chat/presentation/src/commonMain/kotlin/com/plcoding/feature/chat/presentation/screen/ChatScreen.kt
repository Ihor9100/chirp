package com.plcoding.feature.chat.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.style.ChirpTheme
import com.plcoding.core.presentation.screen.base.BaseScreenContent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen(
  viewModel: ChatScreenViewModel = koinViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  BaseScreenContent(
    baseContent = state.baseContent
  ) {
    ChatScreenContent(
      content = state.content,
      onAction = viewModel::onAction
    )
  }
}

@Composable
fun ChatScreenContent(
  content: ChatScreenContent,
  onAction: (ChatScreenAction) -> Unit,
) {
  Text(text = "Hello World!!!")
}

@Preview
@Composable
private fun ChatScreenPreview() {
  ChirpTheme {
    ChatScreenContent(
      content = ChatScreenContent(),
      onAction = {}
    )
  }
}