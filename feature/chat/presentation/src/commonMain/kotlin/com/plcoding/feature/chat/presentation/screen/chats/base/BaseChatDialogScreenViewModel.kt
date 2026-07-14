@file:OptIn(FlowPreview::class)

package com.plcoding.feature.chat.presentation.screen.chats.base

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_participant_found
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.mapOn
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.getStringRes
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.presentation.mapper.toUi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.seconds

abstract class BaseChatDialogScreenViewModel<ContentUi : BaseChatDialogUiState<ContentUi>>(
  private val chatRepository: ChatRepository,
) : BaseScreenViewModel<ContentUi>() {

  protected abstract fun handlePositiveClick()

  private val searchQueryFlow =
    snapshotFlow { screenUiState.value.uiState.searchTextFieldState.text }
      .debounce(1.seconds)
      .filter { it.isNotBlank() }
      .onEach(::searchMember)

  override fun onInitialize() {
    super.onInitialize()
    searchQueryFlow.launchIn(viewModelScope)
  }

  fun onAction(action: BaseChatDialogScreenAction) {
    when (action) {
      BaseChatDialogScreenAction.OnAddClick -> handleAddClick()
      BaseChatDialogScreenAction.OnPositiveClick -> handlePositiveClick()
      else -> Unit
    }
  }

  private fun handleAddClick() {
    updateUiState {
      val doNothing = foundChatMemberUi == null ||
        foundChatMembersUi.contains(foundChatMemberUi) ||
        inChatMembersUi.contains(foundChatMemberUi)

      if (doNothing) return@updateUiState this

      searchTextFieldState.also { it.clearText() }
      update(null, foundChatMembersUi + foundChatMemberUi!!)
    }
  }

  private fun searchMember(searchQuery: CharSequence) {
    launchLoadable {
      updateUiState { update(foundChatMemberUi = null) }

      chatRepository
        .searchMember(searchQuery.toString())
        .mapOn { it.toUi(isInChat = false) }
        .onFailure(::handleSearchMemberFailure)
        .onSuccess { updateUiState { update(foundChatMemberUi = it) } }
    }
  }

  private fun handleSearchMemberFailure(error: DataError.Remote) {
    val errorRes = when (error) {
      DataError.Remote.NOT_FOUND -> Res.string.no_participant_found
      else -> error.getStringRes()
    }
    showSnackbar(errorRes)
  }
}
