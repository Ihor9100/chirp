@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package com.plcoding.feature.chat.presentation.screen.user.profile

import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.domain.repository.LiveChatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.uuid.ExperimentalUuidApi

class UserProfileDialogScreenViewModel(
  private val preferencesRepository: PreferencesRepository,
  private val chatRepository: ChatRepository,
  private val liveChatRepository: LiveChatRepository,
) : BaseScreenViewModel<UserProfileDialogScreenUiState>() {

  override fun getUiState(): UserProfileDialogScreenUiState {
    return UserProfileDialogScreenUiState()
  }

  override fun onInitialize() {
    super.onInitialize()
  }

  fun handleAction(action: UserProfileDialogScreenAction){
    when(action){
      UserProfileDialogScreenAction.OnBackClick -> TODO()
      UserProfileDialogScreenAction.OnMenuClick -> TODO()
      UserProfileDialogScreenAction.OnMenuDismiss -> TODO()
      is UserProfileDialogScreenAction.OnMenuItemClick -> TODO()
      is UserProfileDialogScreenAction.OnMessageLongClick -> TODO()
      UserProfileDialogScreenAction.OnMessageMenuDismiss -> TODO()
      is UserProfileDialogScreenAction.OnMessageMenuItemClick -> TODO()
      is UserProfileDialogScreenAction.OnMessageRetryClick -> TODO()
      UserProfileDialogScreenAction.OnPageRetryClick -> TODO()
      is UserProfileDialogScreenAction.OnScroll -> TODO()
      UserProfileDialogScreenAction.OnScrollToStartClick -> TODO()
      UserProfileDialogScreenAction.OnSendClick -> TODO()
    }
  }
}
