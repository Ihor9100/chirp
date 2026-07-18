@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package com.plcoding.feature.chat.presentation.screen.chats.details

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import chirp.core.designsystem.generated.resources.ic_cloud_off
import chirp.core.designsystem.generated.resources.ic_users
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.ic_log_out
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.select_chat_subtitle
import chirp.feature.chat.presentation.generated.resources.success
import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.toStringRes
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import com.plcoding.feature.chat.domain.model.ConnectionState
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.domain.repository.LiveChatRepository
import com.plcoding.feature.chat.presentation.mapper.toChatHeaderUi
import com.plcoding.feature.chat.presentation.mapper.toUi
import com.plcoding.feature.chat.presentation.model.ChatEmptyStateUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import chirp.core.designsystem.generated.resources.Res as CoreRes

class ChatDetailsScreenViewModel(
  private val preferencesRepository: PreferencesRepository,
  private val chatRepository: ChatRepository,
  private val liveChatRepository: LiveChatRepository,
) : BaseScreenViewModel<ChatDetailsScreenUiState>() {

  private val _chatId = MutableStateFlow<String?>(null)

  private val _chatDetails = _chatId
    .map { it.orEmpty() }
    .flatMapLatest(chatRepository::observeChatDetails)

  override fun getUiState(): ChatDetailsScreenUiState {
    return ChatDetailsScreenUiState.mock
  }

  override fun onInitialize() {
    super.onInitialize()

    observeConnectionState()
    observeScrollToBottomEvent()
    observeChatDetails()
    observeCanSendMessage()
  }

  private fun observeConnectionState() {
    liveChatRepository
      .connectionState
      .distinctUntilChanged()
      .onEach {
        val isConnected = it == ConnectionState.CONNECTED

        if (isConnected) {
          _chatId.firstOrNull()?.let { chatId ->
            chatRepository.syncChatMessages(chatId, null)
          }
        }

        updateUiState {
          val connectionIconRes = if (isConnected) null else CoreRes.drawable.ic_cloud_off
          val textFieldUi = multilineTextFieldUi.copy(connectionIconRes = connectionIconRes)
          copy(multilineTextFieldUi = textFieldUi)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun observeScrollToBottomEvent() {
    val currentMessages = screenUiState
      .map { it.uiState.chatMessagesUi }
      .distinctUntilChanged()

    val newMessages = _chatDetails
      .map { it?.chatMessagesAndMembers }

    combine(
      currentMessages,
      newMessages,
    ) { currentMessages, newMessages ->
      val lastCurrent = currentMessages?.lastOrNull()?.id
      val lastNew = newMessages?.lastOrNull()?.chatMessage?.id

      if (lastCurrent != lastNew) {
        updateUiState { copy(scrollToBottom = Event(Unit)) }
      }
    }
      .launchIn(viewModelScope)
  }

  private fun observeChatDetails() {
    combine(
      preferencesRepository.observeAuthInfo(),
      _chatId,
      _chatDetails,
    ) { authInfo, chatId, chatDetails ->
      val userId = authInfo?.user?.id

      screenUiState.value.uiState.copy(
        chatEmptyStateUi = getChatEmptyStateUi(chatId),
        chatHeaderUi = chatDetails?.chat?.toChatHeaderUi(authInfo?.user?.id),
        chatMessagesUi = chatDetails?.chatMessagesAndMembers?.map { it.toUi(userId) },
      )
    }
      .flowOn(Dispatchers.IO)
      .onEach { content -> updateUiState { content } }
      .launchIn(viewModelScope)
  }

  private fun observeCanSendMessage() {
    snapshotFlow { screenUiState.value.uiState.multilineTextFieldUi.textFieldState.text.toString() }
      .combine(liveChatRepository.connectionState) { text, connectionState ->
        text.isNotBlank() && connectionState == ConnectionState.CONNECTED
      }
      .onEach { canSendMessage ->
        updateUiState {
          val textFieldUi = multilineTextFieldUi.copy(isButtonEnabled = canSendMessage)
          copy(multilineTextFieldUi = textFieldUi)
        }
      }
      .launchIn(viewModelScope)
  }

  fun loadChat(chatId: String?) {
    _chatId.update { chatId }

    if (chatId != null) {
      viewModelScope.launch {
        chatRepository
          .syncChat(chatId)
          .onFailure { showSnackbar(it.toStringRes()) }
      }
    }
  }

  fun handleAction(action: ChatDetailsScreenAction) {
    when (action) {
      is ChatDetailsScreenAction.OnMenuClick -> {
        updateUiState { copy(dropDownItemsUi = getChatsDropDownItems()) }
      }
      is ChatDetailsScreenAction.OnMenuDismissClick -> {
        updateUiState { copy(dropDownItemsUi = null) }
      }
      is ChatDetailsScreenAction.OnMenuItemClick -> {
        handleChatDetailsMenuItemClick(action.dropDownItemPm)
      }
      is ChatDetailsScreenAction.OnRetryClick -> {
        resendMessage(action.messageId)
      }
      is ChatDetailsScreenAction.OnSendClick -> {
        sendMessage()
      }
      else -> Unit
    }
  }

  private fun sendMessage() {
    viewModelScope.launch {
      val chatId = _chatId.value
      val senderId = preferencesRepository.observeAuthInfo().first()?.user?.id

      if (chatId == null || senderId == null) return@launch

      val chatMessage = ChatMessage(
        id = Uuid.random().toString(),
        chatId = chatId,
        senderId = senderId,
        content = screenUiState.value.uiState.multilineTextFieldUi.textFieldState.text.toString(),
        createdAt = Clock.System.now(),
        deliveryStatus = ChatMessageDeliveryStatus.SENDING,
      )

      liveChatRepository
        .sendMessage(chatMessage)
        .onSuccess { screenUiState.value.uiState.multilineTextFieldUi.textFieldState.clearText() }
        .onFailure { showSnackbar(it.toStringRes()) }
    }
  }

  private fun resendMessage(messageId: String) {
    viewModelScope.launch {
      liveChatRepository
        .resendMessage(messageId)
        .onFailure { showSnackbar(it.toStringRes()) }
    }
  }

  private fun handleChatDetailsMenuItemClick(dropDownItemPm: DropDownItemUi) {
    when (dropDownItemPm.titleRes) {
      Res.string.chat_members -> updateUiState {
        copy(openChatManageEvent = Event(_chatId.value!!))
      }
      Res.string.log_out -> {
        leaveChat()
      }
    }
  }

  private fun leaveChat() {
    viewModelScope.launch {
      chatRepository
        .leaveChat(_chatId.value.orEmpty())
        .onFailure { showSnackbar(it.toStringRes()) }
        .onSuccess {
          showSnackbar(Res.string.success)
          updateUiState { copy(leaveChatEvent = Event(Unit)) }
        }
    }
  }

  private fun getChatEmptyStateUi(chatId: String?): ChatEmptyStateUi? {
    if (chatId != null) return null

    return ChatEmptyStateUi(
      titleRes = Res.string.no_messages,
      descriptionRes = Res.string.select_chat_subtitle,
    )
  }

  private fun getChatsDropDownItems(): List<DropDownItemUi> {
    return listOf(
      DropDownItemUi(
        leadingIconRes = CoreRes.drawable.ic_users,
        titleRes = Res.string.chat_members,
        colorToken = ColorToken.TextSecondary,
      ),
      DropDownItemUi(
        leadingIconRes = Res.drawable.ic_log_out,
        titleRes = Res.string.log_out,
        colorToken = ColorToken.TextDestructive,
      ),
    )
  }
}
