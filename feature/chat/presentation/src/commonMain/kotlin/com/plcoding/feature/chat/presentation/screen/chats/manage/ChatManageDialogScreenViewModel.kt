@file:OptIn(FlowPreview::class)

package com.plcoding.feature.chat.presentation.screen.chats.manage

import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import com.plcoding.feature.chat.presentation.screen.chats.base.BaseChatDialogScreenViewModel
import kotlinx.coroutines.FlowPreview

class ChatManageDialogScreenViewModel(
  chatRepository: ChatRepository,
  chatMemberPmMapper: ChatMemberPmMapper,
) : BaseChatDialogScreenViewModel<ChatManageDialogScreenContentPm>(
  chatRepository,
  chatMemberPmMapper,
) {

  override fun getContentPm(): ChatManageDialogScreenContentPm {
    return ChatManageDialogScreenContentPm()
  }
}
