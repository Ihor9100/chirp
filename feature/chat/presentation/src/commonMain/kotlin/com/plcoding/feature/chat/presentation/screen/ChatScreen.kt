package com.plcoding.feature.chat.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.core.designsystem.style.Theme
import com.plcoding.core.presentation.screen.base.BaseScreenContent
import com.plcoding.core.presentation.screen.base.BaseScreenState
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

@Composable
private fun Themed(
  isDarkTheme: Boolean,
) {
  val baseScreenState = BaseScreenState(ChatScreenContent())

  Theme(isDarkTheme) {
    BaseScreenContent(
      baseContent = baseScreenState.baseContent
    ) {
      ChatScreenContent(
        content = baseScreenState.content,
        onAction = {}
      )
    }
  }
}

@Preview
@Composable
private fun LightPreview() {
  Themed(
    isDarkTheme = false,
  )
}

@Preview
@Composable
private fun DarkPreview() {
  Themed(
    isDarkTheme = true
  )
}