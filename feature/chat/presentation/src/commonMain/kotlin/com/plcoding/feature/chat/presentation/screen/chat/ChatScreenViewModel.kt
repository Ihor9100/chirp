package com.plcoding.feature.chat.presentation.screen.chat

import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_participant_found
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.screen.base.Overlay
import com.plcoding.core.presentation.utils.getStringRes

class ChatScreenViewModel() : BaseScreenViewModel<ChatScreenContent>() {

  override fun getInitialContent(): ChatScreenContent {
    return ChatScreenContent()
  }

  fun onAction(action: ChatScreenAction) {
    when (action) {
      is ChatScreenAction.OnChatClick -> {
        val errorRes = Res.string.no_participant_found

        updateBaseContent {
          copy(overlays = setOf(Overlay.Snackbar(Event(errorRes))))
        }

//        updateContent {
//          copy(chatId = action.chatId)
//        }
      }
    }
  }
}
