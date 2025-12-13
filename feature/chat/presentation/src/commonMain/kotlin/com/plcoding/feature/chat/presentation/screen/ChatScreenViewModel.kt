package com.plcoding.feature.chat.presentation.screen

import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.repository.local.PreferencesLocalRepository
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatScreenViewModel(
  private val preferencesLocalRepository: PreferencesLocalRepository,
) : BaseScreenViewModel<ChatState>() {

  override fun getInitialState() = ChatState()

  init {
    viewModelScope.launch {
      delay(5000)
      preferencesLocalRepository.saveAuthInfo(null)
    }
  }

  fun onAction(action: ChatAction) {
    when (action) {
      else -> TODO("Handle actions")
    }
  }
}
