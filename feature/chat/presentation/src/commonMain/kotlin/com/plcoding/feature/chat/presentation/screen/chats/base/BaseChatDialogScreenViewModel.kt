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
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.seconds

abstract class BaseChatDialogScreenViewModel<ContentPm : BaseChatDialogScreenContentPm<ContentPm>>(
  private val chatRepository: ChatRepository,
  private val chatMemberPmMapper: ChatMemberPmMapper,
) : BaseScreenViewModel<ContentPm>() {

  protected abstract fun handlePositiveClick()

  private val searchQueryFlow =
    snapshotFlow { screenState.value.contentPm.searchTextFieldState.text }
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
    updateContentPm {
      val doNothing = foundChatMemberPm == null ||
        foundChatMembersPm.contains(foundChatMemberPm) ||
        inChatMembersPm.contains(foundChatMemberPm)

      if (doNothing) return@updateContentPm this

      searchTextFieldState.also { it.clearText() }
      update(null, foundChatMembersPm + foundChatMemberPm!!)
    }
  }

  private fun searchMember(searchQuery: CharSequence) {
    launchLoadable {
      updateContentPm { update(foundChatMemberPm = null) }

      chatRepository
        .searchMember(searchQuery.toString())
        .mapOn { chatMemberPmMapper.map(it) }
        .onFailure(::handleSearchMemberFailure)
        .onSuccess { updateContentPm { update(foundChatMemberPm = it) } }
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
