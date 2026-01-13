package com.plcoding.feature.chat.presentation.screen.chat.create

import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.screen.base.Overlay
import kotlinx.coroutines.delay

class ChatCreateScreenViewModel : BaseScreenViewModel<ChatCreateScreenContent>() {

  override fun getInitialContent(): ChatCreateScreenContent {
    return ChatCreateScreenContent()
  }

  fun onAction(action: ChatCreateScreenAction) {
    when (action) {
      else -> launchLoadable {
        delay(5000)
      }
    }
  }
}