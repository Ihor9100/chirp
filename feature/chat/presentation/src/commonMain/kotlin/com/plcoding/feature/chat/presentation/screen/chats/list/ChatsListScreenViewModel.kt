@file:OptIn(ExperimentalCoroutinesApi::class)

package com.plcoding.feature.chat.presentation.screen.chats.list

import androidx.lifecycle.viewModelScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.no_messages_subtitle
import com.plcoding.core.designsystem.model.AvatarSizeUi
import com.plcoding.core.designsystem.model.AvatarUi
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.toStringRes
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.presentation.mapper.toUiList
import com.plcoding.feature.chat.presentation.model.ChatEmptyStateUi
import com.plcoding.feature.chat.presentation.utils.FormatUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatsListScreenViewModel(
  private val preferencesRepository: PreferencesRepository,
  private val chatRepository: ChatRepository,
) : BaseScreenViewModel<ChatsListScreenUiState>() {

  private val _chatId = MutableStateFlow<String?>(null)

  override fun getUiState(): ChatsListScreenUiState {
    return ChatsListScreenUiState()
  }

  override fun onInitialize() {
    super.onInitialize()

    loadChats()
    observeScreenData()
  }

  private fun loadChats() {
    launchLoadable {
      chatRepository
        .syncChats()
        .onFailure { showSnackbar(it.toStringRes()) }
    }
  }

  private fun observeScreenData() {
    combine(
      preferencesRepository.observeAuthInfo(),
      chatRepository.observeChats(),
      _chatId,
    ) { authInfo, chats, chatId ->
      ChatsListScreenUiState(
        avatarUi = getAvatarUi(authInfo),
        chatEmptyStateUi = getChatEmptyStateUi(chats),
        chatsUi = chats.toUiList(authInfo?.user?.id, chatId),
      )
    }
      .flowOn(Dispatchers.IO)
      .onEach {
        updateUiState {
          copy(
            avatarUi = it.avatarUi,
            chatEmptyStateUi = it.chatEmptyStateUi,
            chatsUi = it.chatsUi,
          )
        }
      }
      .launchIn(viewModelScope)
  }

  private fun getChatEmptyStateUi(chats: List<Chat>): ChatEmptyStateUi? {
    return if (chats.isEmpty()) {
      ChatEmptyStateUi(
        titleRes = Res.string.no_messages,
        descriptionRes = Res.string.no_messages_subtitle,
      )
    } else {
      null
    }
  }

  private fun getAvatarUi(authInfo: AuthInfo?): AvatarUi {
    return AvatarUi(
      initials = FormatUtils.getInitials(authInfo?.user?.username.orEmpty()),
      imageUrl = authInfo?.user?.profilePictureUrl,
      avatarSizeUi = AvatarSizeUi.MEDIUM,
    )
  }

  fun handleAction(action: ChatsListScreenAction) {
    when (action) {
      is ChatsListScreenAction.OnChatClick -> {
        _chatId.value = action.chatId
      }
      is ChatsListScreenAction.OnUserAvatarClick -> updateUiState {
        copy(showDropDownMenu = true)
      }
      is ChatsListScreenAction.OnDropDownMenuDismiss -> updateUiState {
        copy(showDropDownMenu = false)
      }
      else -> Unit
    }
  }
}
