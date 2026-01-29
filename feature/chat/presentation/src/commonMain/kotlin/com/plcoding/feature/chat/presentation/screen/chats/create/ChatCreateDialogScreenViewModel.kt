@file:OptIn(FlowPreview::class)

package com.plcoding.feature.chat.presentation.screen.chats.create

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_participant_found
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.mapOn
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.getStringRes
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.repository.remote.ChatRemoteRepository
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.seconds

class ChatCreateDialogScreenViewModel(
  private val chatRemoteRepository: ChatRemoteRepository,
  private val chatMemberPmMapper: ChatMemberPmMapper,
) : BaseScreenViewModel<ChatCreateDialogScreenContentPm>() {

  private val searchQueryFlow = snapshotFlow { screenState.value.contentPm.searchTextFieldState.text }
    .debounce(1.seconds)
    .filter { it.isNotBlank() }
    .onEach(::searchMember)

  override fun getContentPm(): ChatCreateDialogScreenContentPm {
    return ChatCreateDialogScreenContentPm()
  }

  override fun onInitialized() {
    super.onInitialized()
    searchQueryFlow.launchIn(viewModelScope)
  }

  fun onAction(action: ChatCreateDialogScreenAction) {
    when (action) {
      ChatCreateDialogScreenAction.OnAddClick -> handleAddClick()
      ChatCreateDialogScreenAction.OnCreateDialogClick -> handleCreateClick()
      else -> Unit
    }
  }

  private fun handleAddClick() {
    updateContentPm {
      if (chatMemberPm == null || chatMembersPm.contains(chatMemberPm))
        return@updateContentPm this

      copy(
        searchTextFieldState = searchTextFieldState.also { it.clearText() },
        chatMemberPm = null,
        chatMembersPm = chatMembersPm + chatMemberPm,
      )
    }
  }

  private fun handleCreateClick() {
    val memberIds = screenState.value.contentPm.chatMembersPm.map { it.id }
    if (memberIds.isEmpty()) return

    launchLoadable {
      chatRemoteRepository
        .createChat(memberIds)
        .onFailure { showSnackbar(it.getStringRes()) }
        .onSuccess(::handleCreateSuccess)
    }
  }

  private fun handleCreateSuccess(chat: Chat) {
    updateContentPm {
      copy(chatCreatedEvent = Event(chat))
    }
  }

  private fun searchMember(searchQuery: CharSequence) {
    launchLoadable {
      updateContentPm { copy(chatMemberPm = null) }

      chatRemoteRepository
        .searchMember(searchQuery.toString())
        .mapOn { chatMemberPmMapper.map(it, Unit) }
        .onFailure(::handleSearchMemberFailure)
        .onSuccess(::handleSearchMemberSuccess)
    }
  }

  private fun handleSearchMemberFailure(error: DataError.Remote) {
    val errorRes = when (error) {
      DataError.Remote.NOT_FOUND -> Res.string.no_participant_found
      else -> error.getStringRes()
    }
    showSnackbar(errorRes)
  }

  private fun handleSearchMemberSuccess(chatMemberPm: ChatMemberPm) {
    updateContentPm {
      copy(chatMemberPm = chatMemberPm)
    }
  }
}
