@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.feature.chat.presentation.screen.chats

import androidx.lifecycle.viewModelScope
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.feature.chat.domain.repository.LiveChatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn

class ChatsScreenViewModel(
  private val liveChatRepository: LiveChatRepository,
) : BaseScreenViewModel<Unit>() {

  override fun getUiState(): Unit {
    return Unit
  }

  override fun onInitialize() {
    super.onInitialize()
    observeChatMessage()
  }

  private fun observeChatMessage() {
    liveChatRepository
      .chatMessage
      .launchIn(viewModelScope)
  }
}
