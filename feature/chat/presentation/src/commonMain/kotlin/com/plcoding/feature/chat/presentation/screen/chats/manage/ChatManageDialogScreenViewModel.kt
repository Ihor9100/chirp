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
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatManageDialogScreenViewModel(
  savedStateHandle: SavedStateHandle,
  private val chatRepository: ChatRepository,
  private val chatMemberPmMapper: ChatMemberPmMapper,
) : BaseChatDialogScreenViewModel<ChatManageDialogScreenContentPm>(
  chatRepository,
  chatMemberPmMapper,
) {

  private val _chatId = MutableStateFlow<String>(savedStateHandle.get<String>("chatId")!!)
  private val _chatMembers = _chatId
    .flatMapLatest { chatRepository.observeChatMembers(it) }
    .map(::getChatMembersPm)
    .flowOn(Dispatchers.IO)
    .onEach { updateContentPm { copy(inChatMembersPm = it) } }
    .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

  override fun getContentPm(): ChatManageDialogScreenContentPm {
    return ChatManageDialogScreenContentPm()
  }

  private fun getChatMembersPm(chatMembers: List<ChatMember>): List<ChatMemberPm> {
    return chatMembers.map {
      val from = ChatMemberPmMapper.From(it, isInChat = true)
      chatMemberPmMapper.map(from)
    }
  }

  override fun handlePositiveClick() {
    val chatMembersId = _chatMembers.value.map { it.id }
    if (chatMembersId.isEmpty()) return

    viewModelScope.launch {
      chatRepository
        .addChatMembers(_chatId.value, chatMembersId)
        .onFailure { showSnackbar(it.getStringRes()) }
        .onSuccess { updateContentPm { copy(chatUpdatedEvent = Event(Unit)) } }
    }
  }
}
