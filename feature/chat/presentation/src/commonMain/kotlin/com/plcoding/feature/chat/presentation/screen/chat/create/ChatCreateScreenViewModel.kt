package com.plcoding.feature.chat.presentation.screen.chat.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ChatCreateScreenViewModel : BaseScreenViewModel<ChatCreateScreenContent>() {

  override fun getInitialContent(): ChatCreateScreenContent {
    TODO("Not yet implemented")
  }

  fun onAction(action: ChatCreateScreenAction) {
    when (action) {
      else -> TODO("Handle actions")
    }
  }
}