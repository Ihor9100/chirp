@file:OptIn(FlowPreview::class)

package com.plcoding.feature.chat.presentation.screen.chat.create

import androidx.compose.runtime.snapshotFlow
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_participant_found
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.map
import com.plcoding.core.domain.result.mapOn
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.getStringRes
import com.plcoding.feature.chat.domain.repository.remote.ChatRemoteRepository
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.seconds

class ChatCreateScreenViewModel(
  private val chatRemoteRepository: ChatRemoteRepository,
  private val chatMemberPmMapper: ChatMemberPmMapper,
) : BaseScreenViewModel<ChatCreateScreenContent>() {

  private val searchQueryFlow = snapshotFlow { state.value.content.searchTextFieldState.text }
    .debounce(1.seconds)
    .onEach(::searchMember)

  override fun getInitialContent(): ChatCreateScreenContent {
    return ChatCreateScreenContent()
  }

  fun onAction(action: ChatCreateScreenAction) {
    when (action) {
      else -> launchLoadable {
        delay(5000)
      }
    }
  }

  private fun searchMember(searchQuery: CharSequence) {
    launchLoadable {
      chatRemoteRepository
        .searchMember(searchQuery.toString())
        .mapOn { chatMemberPmMapper.map(it, Unit) }
        .onFailure(::handleFailure)
        .onSuccessго { }
    }
  }

  private fun getChatMemberPm(): ChatMemberPm {

  }

  private fun handleFailure(error: DataError.Remote) {
    val errorRes = when (error) {
      DataError.Remote.NOT_FOUND -> Res.string.no_participant_found
      else -> error.getStringRes()
    }
    updateBaseContent {
      copy(snackbarEvent = Event(errorRes))
    }
  }

  private fun handleSuccess(chat) {
    updateContent {
      copy(snackbarEvent = Event(chirp.feature.auth.presentation.generated.resources.Res.string.resent_verification_email))
    }
  }
}