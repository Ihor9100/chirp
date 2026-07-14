@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.feature.chat.presentation.screen.chats

import androidx.lifecycle.viewModelScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.success
import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.getStringRes
import com.plcoding.feature.chat.domain.model.ConnectionState
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.domain.repository.LiveChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatsScreenViewModel(
  private val preferencesRepository: PreferencesRepository,
  private val chatRepository: ChatRepository,
  private val liveChatRepository: LiveChatRepository,
) : BaseScreenViewModel<ChatsUiState>() {

  private val _internalState = MutableStateFlow(InternalState())

  private val _chatDetails = _internalState
    .map { it.chatId.orEmpty() }
    .flatMapLatest(chatRepository::observeChatDetails)

  override fun getUiState(): ChatsUiState {
    return ChatsUiState.mock
  }

  override fun onInitialize() {
    super.onInitialize()

    loadChats()
    observeWebSocket()
    observeConnectionState()
    observeChatMessages()
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

  private fun observeWebSocket() {
    liveChatRepository
      .chatMessage
      .launchIn(viewModelScope)
  }

  private fun observeConnectionState() {
    liveChatRepository
      .connectionState
      .distinctUntilChanged()
      .onEach {
        if (it == ConnectionState.CONNECTED) {
          val chatId = _internalState.firstOrNull()?.chatId
          chatId?.let { chatRepository.syncChatMessages(chatId, null) }
        }

        // TODO: update uiState ConnectionState
      }
      .launchIn(viewModelScope)
  }

  private fun observeChatMessages() {
    val currentMessages = screenUiState
      .map { it.uiState.chatMessagesUi }
      .distinctUntilChanged()

    val newMessages = _chatDetails
      .map { it?.chatMessagesAndMembers }

    combine(
      currentMessages,
      newMessages,
    ) { currentMessages, newMessages ->
      val lastCurrent = currentMessages?.lastOrNull()?.id
      val lastNew = newMessages?.lastOrNull()?.chatMessage?.id

      if (lastCurrent != lastNew) {
        _internalState.update {
          it.copy(scrollToBottom = Event(Unit))
        }
      }
    }.launchIn(viewModelScope)
  }

  private fun observeScreenData() {
    combine(
      preferencesRepository.observeAuthInfo(),
      chatRepository.observeChats(),
      _internalState,
      _chatDetails,
    ) { authInfo, chats, internalState, chatDetails ->
      ChatsUiState.from(
        yourId = authInfo?.user?.id,
        chats = chats,
        chatDetails = chatDetails,
        internalState = internalState,
      )
    }
      .flowOn(Dispatchers.IO)
      .onEach { content -> updateUiState { content } }
      .launchIn(viewModelScope)
  }

  fun openChatDetails(chatId: String) {
    _internalState.update { it.copy(chatId = chatId, leaveChatEvent = null) }
    loadChat(chatId)
  }

  fun clearChatId() {
    _internalState.update { it.copy(chatId = null) }
  }

  fun handleAction(chatsScreenAction: ChatsScreenAction) {
    when (chatsScreenAction) {
      is ChatsScreenAction.OnChatDetailsBackClick -> {
        clearChatId()
      }
      is ChatsScreenAction.OnChatDetailsMenuClick -> {
        _internalState.update { it.copy(showChatDetailsDropDown = true) }
      }
      is ChatsScreenAction.OnChatDetailsMenuDismissClick -> {
        _internalState.update { it.copy(showChatDetailsDropDown = false) }
      }
      is ChatsScreenAction.OnChatDetailsMenuItemClick -> {
        handleChatDetailsMenuItemClick(chatsScreenAction.dropDownItemPm)
      }
      else -> Unit
    }
  }

  private fun handleChatDetailsMenuItemClick(dropDownItemPm: DropDownItemUi) {
    when (dropDownItemPm.titleRes) {
      Res.string.chat_members -> _internalState.update {
        it.copy(
          showChatDetailsDropDown = false,
          openChatManageEvent = Event(_internalState.value.chatId!!),
        )
      }
      Res.string.log_out -> {
        leaveChat()
      }
    }
  }

  private fun leaveChat() {
    viewModelScope.launch {
      chatRepository
        .leaveChat(_internalState.value.chatId.orEmpty())
        .onFailure { showSnackbar(it.getStringRes()) }
        .onSuccess {
          showSnackbar(Res.string.success)
          _internalState.update {
            it.copy(
              chatId = null,
              showChatDetailsDropDown = false,
              leaveChatEvent = Event(Unit),
            )
          }
        }
    }
  }

  data class InternalState(
    val chatId: String? = null,
    val showChatDetailsDropDown: Boolean = false,
    val openChatManageEvent: Event<String>? = null,
    val leaveChatEvent: Event<Unit>? = null,
    val scrollToBottom: Event<Unit>? = null,
  )
}
