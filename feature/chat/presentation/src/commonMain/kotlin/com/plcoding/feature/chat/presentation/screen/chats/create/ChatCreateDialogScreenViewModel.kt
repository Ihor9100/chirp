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
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.getStringRes
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import com.plcoding.feature.chat.presentation.model.ChatMemberPm
import com.plcoding.feature.chat.presentation.screen.chats.create.ChatCreateDialogScreenContentPm
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.seconds

class ChatCreateDialogScreenViewModel(
  chatRepository: ChatRepository,
  chatMemberPmMapper: ChatMemberPmMapper,
) : BaseChatDialogScreenViewModel<ChatCreateDialogScreenContentPm>(
  chatRepository,
  chatMemberPmMapper,
) {

  override fun getContentPm(): ChatCreateDialogScreenContentPm {
    return ChatCreateDialogScreenContentPm()
  }
}
