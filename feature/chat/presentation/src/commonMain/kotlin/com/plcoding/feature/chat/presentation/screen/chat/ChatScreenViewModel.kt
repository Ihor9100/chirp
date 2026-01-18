package com.plcoding.feature.chat.presentation.screen.chat

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_participant_found
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import kotlinx.coroutines.delay

class ChatScreenViewModel() : BaseScreenViewModel<ChatScreenContent>() {

  override fun getInitialContent(): ChatScreenContent {
    return ChatScreenContent()
  }

  fun onAction(action: ChatScreenAction) {
    when (action) {
      is ChatScreenAction.OnChatClick -> {
        val errorRes = Res.string.no_participant_found

//        updateBaseContent {
//          copy(overlays = setOf(Overlay.Snackbar(Event(errorRes))))
//        }

        launchLoadable {
          delay(5000)
        }
//        updateContent {
//          copy(chatId = action.chatId)
//        }
      }
    }
  }
}
