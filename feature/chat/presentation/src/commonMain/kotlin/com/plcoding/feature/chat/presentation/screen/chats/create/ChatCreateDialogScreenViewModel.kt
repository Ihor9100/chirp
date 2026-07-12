@file:OptIn(FlowPreview::class)

package com.plcoding.feature.chat.presentation.screen.chats.create

import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.utils.getStringRes
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenViewModel
import kotlinx.coroutines.FlowPreview

class ChatCreateDialogScreenViewModel(
  private val chatRepository: ChatRepository,
) : BaseChatDialogScreenViewModel<ChatCreateDialogScreenContentPm>(
  chatRepository,
) {

  override fun getContentPm(): ChatCreateDialogScreenContentPm {
    return ChatCreateDialogScreenContentPm()
  }

  override fun handlePositiveClick() {
    val memberIds = screenState.value.contentPm.foundChatMembersPm.map { it.id }
    if (memberIds.isEmpty()) return

    launchLoadable {
      chatRepository
        .createChat(memberIds)
        .onFailure { showSnackbar(it.getStringRes()) }
        .onSuccess { updateContentPm { copy(chatCreatedEvent = Event(Unit)) } }
    }
  }
}
