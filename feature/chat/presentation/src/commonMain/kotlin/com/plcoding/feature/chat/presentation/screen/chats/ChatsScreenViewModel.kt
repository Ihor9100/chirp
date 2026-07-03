@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.feature.chat.presentation.screen.chats

import androidx.lifecycle.viewModelScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.success
import com.plcoding.core.designsystem.model.DropDownItemPm
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.getStringRes
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
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
  private var isChatMember: Boolean = true
  private var showChatDetailsDropDown: Boolean = false

  override fun getContentPm(): ChatsScreenContentPm {
    return ChatsScreenContentPm.mock
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
        .onFailure { showSnackbar(it.getStringRes()) }
    }
  }

  private fun loadChat(chatId: String) {
    viewModelScope.launch {
      chatRepository
        .syncChat(chatId)
        .onFailure { showSnackbar(it.getStringRes()) }
    }
  }

  private fun observeScreenData() {
    screenJob?.cancel()
    screenJob = viewModelScope.launch {
      combine(
        _chatId,
        preferencesRepository.observeAuthInfo(),
        chatRepository.observeChats(),
        _chatDetails,
      ) { chatId, authInfo, chats, chatDetails ->
        contentPmMapper.map(
          ChatsScreenContentPmMapper.From(
            yourId = authInfo?.user?.id,
            chatId = chatId,
            chats = chats,
            chatDetails = chatDetails,
            showChatDetailsDropDown = showChatDetailsDropDown,
            isChatMember = isChatMember,
          )
        )
      }
        .flowOn(Dispatchers.IO)
        .collect { updateContentPm { it } }
    }
  }

  private suspend fun observeChatDetails(chatId: String?): Flow<ChatDetails?> {
    return if (chatId == null) {
      flowOf(null)
    } else {
      chatRepository.observeChatDetails(chatId)
    }
  }

  fun openChatDetails(chatId: String) {
    isChatMember = true
    _chatId.value = chatId
    loadChat(chatId)
  }

  fun handleAction(chatsScreenAction: ChatsScreenAction) {
    when (chatsScreenAction) {
      is ChatsScreenAction.OnChatDetailsMenuClick -> {
        showChatDetailsDropDown = true
        observeScreenData()
      }
      is ChatsScreenAction.OnChatDetailsMenuDismissClick -> {
        showChatDetailsDropDown = false
        observeScreenData()
      }
      is ChatsScreenAction.OnChatDetailsMenuItemClick -> {
        handleMenuItem(chatsScreenAction.dropDownItemPm)
      }
      else -> Unit
    }
  }

  private fun handleMenuItem(dropDownItemPm: DropDownItemPm) {
    when (dropDownItemPm.titleRes) {
      Res.string.chat_members -> showSnackbar(dropDownItemPm.titleRes)
      Res.string.log_out -> leaveChat()
    }
  }

  private fun leaveChat() {
    viewModelScope.launch {
      chatRepository
        .leaveChat(_chatId.value.orEmpty())
        .onFailure { showSnackbar(it.getStringRes()) }
        .onSuccess {
          showSnackbar(Res.string.success)
          isChatMember = false
          showChatDetailsDropDown = false
          observeScreenData()
        }
    }
  }
}
