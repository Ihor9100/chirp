package com.plcoding.feature.chat.presentation.screen.chats

import androidx.lifecycle.SavedStateHandle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.presentation.mapper.ChatPmMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ChatsScreenViewModel(
  private val savedStateHandle: SavedStateHandle,
  private val chatRepository: ChatRepository,
  private val chatPmMapper: ChatPmMapper,
) : BaseScreenViewModel<ChatsScreenContentPm>() {

  override fun getContentPm(): ChatsScreenContentPm {
    return ChatsScreenContentPm.mock
  }

  override fun onInitialize() {
    super.onInitialize()

  }

  private fun loadScreenData() {
    launchLoadable {
      chatRepository
        .getChats()
        .onFailure {
          // TODO:
        }
    }
  }

  private fun subscribeToScreeData() {
    launch {
      combine(
        chatRepository.subscribeToChats(),
      ) {

      }
        .map {
          // TODO:
          chatPmMapper.mapList(it, ChatPmMapper.Params())
        }
        .flowOn(Dispatchers.IO)
        .collect { }
    }
  }

  fun onAction(onDismiss: () -> Unit) {
    showSnackbar(Res.string.group_chat) {
      onDismiss()
    }
  }

  fun onResult(chat: Chat?) {
    updateContentPm {
      copy(
        chat = chat,
      )
    }
  }
}
