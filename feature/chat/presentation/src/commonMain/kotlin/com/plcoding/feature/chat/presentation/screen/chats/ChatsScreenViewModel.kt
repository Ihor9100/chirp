@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.feature.chat.presentation.screen.chats

import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ChatsScreenViewModel(
  private val preferencesRepository: PreferencesRepository,
  private val chatRepository: ChatRepository,
  private val contentPmMapper: ChatsScreenContentPmMapper,
) : BaseScreenViewModel<ChatsScreenContentPm>() {

  private val _chatId = MutableStateFlow<String?>(null)
  private val _chatDetails = _chatId.flatMapLatest(::observeChatDetails)

  private var screenJob: Job? = null

  override fun getContentPm(): ChatsScreenContentPm {
    return ChatsScreenContentPm.mock
  }

  override fun onInitialize() {
    super.onInitialize()

    loadChats()
    observeScreenData(showDropDown = false)
  }

  private fun loadChats() {
    launchLoadable {
      chatRepository
        .syncChats()
        .onFailure {
          // TODO:
        }
    }
  }

  private fun loadChat(chatId: String) {
    viewModelScope.launch {
      chatRepository
        .syncChat(chatId)
        .onFailure {
          // TODO:
        }
    }
  }

  private fun observeScreenData(showDropDown: Boolean) {
    screenJob?.cancel()
    screenJob = viewModelScope.launch {
      combine(
        _chatId,
        preferencesRepository.observeAuthInfo(),
        chatRepository.observeChats(),
        _chatDetails,
        ::getContentPm,
      )
        .flowOn(Dispatchers.IO)
        .collect { updateContentPm { it } }
    }
  }

  private fun getContentPm(
    chatId: String?,
    authInfo: AuthInfo?,
    chats: List<Chat>,
    chatDetails: ChatDetails?,
  ): ChatsScreenContentPm {
    return contentPmMapper.map(
      ChatsScreenContentPmMapper.From(
        yourId = authInfo?.user?.id,
        chatId = chatId,
        chats = chats,
        chatDetails = chatDetails,
      ),
      Unit,
    )
  }

  private suspend fun observeChatDetails(chatId: String?): Flow<ChatDetails?> {
    return if (chatId == null) {
      flowOf(null)
    } else {
      chatRepository.observeChatDetails(chatId)
    }
  }

  fun openChatDetails(chatId: String) {
    _chatId.value = chatId
    loadChat(chatId)
  }

  fun handleAction(chatsScreenAction: ChatsScreenAction) {
    when (chatsScreenAction) {
      is ChatsScreenAction.OnChatDetailsOptionsClick -> {
        observeScreenData(showDropDown = true)
      }
      else -> Unit
    }
  }
}
