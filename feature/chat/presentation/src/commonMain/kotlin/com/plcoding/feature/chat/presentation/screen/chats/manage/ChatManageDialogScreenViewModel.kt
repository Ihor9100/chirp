@file:OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)

package com.plcoding.feature.chat.presentation.screen.chats.manage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.utils.getStringRes
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.presentation.mapper.toUi
import com.plcoding.feature.chat.presentation.model.ChatMemberUi
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ChatManageDialogScreenViewModel(
  savedStateHandle: SavedStateHandle,
  private val chatRepository: ChatRepository,
) : BaseChatDialogScreenViewModel<ChatManageDialogUiState>(
  chatRepository,
) {

  private val _chatId = MutableStateFlow(savedStateHandle.get<String>("chatId")!!)
  private val _chatMembers = _chatId
    .flatMapLatest { chatRepository.observeChatMembers(it) }
    .map(::getChatMembersUi)
    .flowOn(Dispatchers.IO)
    .onEach { updateUiState { copy(inChatMembersUi = it) } }
    .launchIn(viewModelScope)

  override fun getUiState(): ChatManageDialogUiState {
    return ChatManageDialogUiState()
  }

  private fun getChatMembersUi(chatMembers: List<ChatMember>): List<ChatMemberUi> {
    return chatMembers.map { it.toUi(isInChat = true) }
  }

  override fun handlePositiveClick() {
    val memberIds = screenState.value.uiState.foundChatMembersUi.map { it.id }
    if (memberIds.isEmpty()) return

    viewModelScope.launch {
      chatRepository
        .addChatMembers(_chatId.value, memberIds)
        .onFailure { showSnackbar(it.getStringRes()) }
        .onSuccess { updateUiState { copy(chatUpdatedEvent = Event(Unit)) } }
    }
  }
}
