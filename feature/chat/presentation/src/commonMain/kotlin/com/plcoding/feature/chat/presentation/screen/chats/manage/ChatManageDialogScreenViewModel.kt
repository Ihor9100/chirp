@file:OptIn(FlowPreview::class)

package com.plcoding.feature.chat.presentation.screen.chats.manage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class ChatManageDialogScreenViewModel(
  savedStateHandle: SavedStateHandle,
  chatRepository: ChatRepository,
  chatMemberPmMapper: ChatMemberPmMapper,
) : BaseChatDialogScreenViewModel<ChatManageDialogScreenContentPm>(
  chatRepository,
  chatMemberPmMapper,
) {

  private val _chatId = MutableStateFlow<String>(savedStateHandle.get<String>("chatId")!!)
  private val _chatMembers = _chatId
    .flatMapLatest { chatRepository.observeChatMembers(it) }
    .map {  }
    .flowOn(Dispatchers.IO)
    .onEach {  }
    .stateIn(viewModelScope, SharingStarted.Lazily, listOf())

  override fun getContentPm(): ChatManageDialogScreenContentPm {
    return ChatManageDialogScreenContentPm()
  }

  private fun getChatMembersPm():
  override fun handlePositiveClick() {
    TODO("Not yet implemented")
  }
}
