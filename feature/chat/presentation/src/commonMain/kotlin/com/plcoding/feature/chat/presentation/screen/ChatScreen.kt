package com.plcoding.feature.chat.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.style.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen(
  viewModel: ChatScreenViewModel = koinViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  ChatContent(
    state = state,
    onAction = viewModel::onAction
  )
}

@Composable
fun ChatContent(
  state: ChatState,
  onAction: (ChatAction) -> Unit,
) {
  Text(text = "Hello World!!!")
}

@Preview
@Composable
private fun ChatPreview() {
  ChirpTheme {
    ChatContent(
      state = ChatState(),
      onAction = {}
    )
  }
}