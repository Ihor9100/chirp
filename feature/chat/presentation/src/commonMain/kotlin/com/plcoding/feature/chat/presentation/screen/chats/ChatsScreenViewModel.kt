package com.plcoding.feature.chat.presentation.screen.chats

import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class ChatsScreenViewModel(
  private val preferencesRepository: PreferencesRepository,
  private val chatRepository: ChatRepository,
  private val contentPmMapper: ChatsScreenContentPmMapper,
) : BaseScreenViewModel<ChatsScreenContentPm>() {

  var selectedChatId: String? = null

  override fun getContentPm(): ChatsScreenContentPm {
    return ChatsScreenContentPm.mock
  }

  override fun onInitialize() {
    super.onInitialize()

    loadScreenData()
    observeScreeData()
  }

  private fun loadScreenData() {
    launchLoadable {
      chatRepository
        .syncChats()
        .onFailure {
          // TODO:
        }
    }
  }

  private fun observeScreeData() {
    launch {
      combine(
        preferencesRepository.observeAuthInfo(),
        chatRepository.observeChats(),
      ) { authInfo, chats ->
        contentPmMapper.map(
          chats,
          ChatsScreenContentPmMapper.Params(
            yourId = authInfo?.user?.id,
            selectedChatId = selectedChatId,
          ),
        )
      }
        .flowOn(Dispatchers.IO)
        .collect {
          updateContentPm { it }
        }
    }
  }
}
