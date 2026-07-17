@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.feature.chat.presentation.screen.chats.list

import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.toStringRes
import com.plcoding.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatsListScreenViewModel(
  private val preferencesRepository: PreferencesRepository,
  private val chatRepository: ChatRepository,
) : BaseScreenViewModel<ChatsListScreenUiState>() {

  private val _chatId = MutableStateFlow<String?>(null)

  override fun getUiState(): ChatsListScreenUiState {
    return ChatsListScreenUiState.mock
  }

  override fun onInitialize() {
    super.onInitialize()

    loadChats()
    observeScreenData()
  }

  private fun loadChats() {
    launchLoadable {
      chatRepository
        .syncChats()
        .onFailure { showSnackbar(it.toStringRes()) }
    }
  }

  private fun observeScreenData() {
    combine(
      preferencesRepository.observeAuthInfo(),
      chatRepository.observeChats(),
      _chatId,
    ) { authInfo, chats, chatId ->
      ChatsListScreenUiState.from(
        yourId = authInfo?.user?.id,
        chatId = chatId,
        chats = chats,
      )
    }
      .flowOn(Dispatchers.IO)
      .onEach { content -> updateUiState { content } }
      .launchIn(viewModelScope)
  }

  fun handleAction(action: ChatsListScreenAction) {
    when(action) {
      is ChatsListScreenAction.OnChatClick -> _chatId.value = action.chatId
      else -> Unit
    }
  }
}
