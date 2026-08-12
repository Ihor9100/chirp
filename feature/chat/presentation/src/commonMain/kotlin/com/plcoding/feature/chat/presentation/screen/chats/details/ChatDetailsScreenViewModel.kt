@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)

package com.plcoding.feature.chat.presentation.screen.chats.details

import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import chirp.core.designsystem.generated.resources.ic_cloud_off
import chirp.core.designsystem.generated.resources.ic_users
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.chat_members
import chirp.feature.chat.presentation.generated.resources.delete_for_everyone
import chirp.feature.chat.presentation.generated.resources.ic_leave
import chirp.feature.chat.presentation.generated.resources.leave_the_chat
import chirp.feature.chat.presentation.generated.resources.log_out
import chirp.feature.chat.presentation.generated.resources.no_messages
import chirp.feature.chat.presentation.generated.resources.select_chat_subtitle
import chirp.feature.chat.presentation.generated.resources.success
import chirp.feature.chat.presentation.generated.resources.today
import chirp.feature.chat.presentation.generated.resources.yesterday
import com.plcoding.core.designsystem.model.DropDownItemUi
import com.plcoding.core.designsystem.style.ColorToken
import com.plcoding.core.domain.paging.Paginator
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.onFailure
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.core.presentation.event.Event
import com.plcoding.core.presentation.model.TextProvider
import com.plcoding.core.presentation.screen.base.BaseScreenViewModel
import com.plcoding.core.presentation.utils.toStringRes
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import com.plcoding.feature.chat.domain.model.ConnectionState
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.domain.repository.LiveChatRepository
import com.plcoding.feature.chat.domain.utils.ChatsConstants.PAGE_SIZE
import com.plcoding.feature.chat.presentation.mapper.toChatHeaderUi
import com.plcoding.feature.chat.presentation.mapper.toUiList
import com.plcoding.feature.chat.presentation.model.ChatEmptyStateUi
import com.plcoding.feature.chat.presentation.model.ChatHeaderUi
import com.plcoding.feature.chat.presentation.model.ChatMessageUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
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

  private var paginator: Paginator<String?, ChatMessage>? = null

  override fun getUiState(): ChatDetailsScreenUiState {
    return ChatDetailsScreenUiState.mock
  }

  override fun onInitialize() {
    super.onInitialize()

    observeConnectionState()
    observeChatDetails()
    observeCanSendMessage()
    observeChatId()
  }

  private fun observeConnectionState() {
    liveChatRepository
      .connectionState
      .combine(_chatId) { connectionState, chatId ->
        val isConnected = connectionState == ConnectionState.CONNECTED

        if (isConnected && chatId != null) {
          loadNextChatMessages()
        }

        updateUiState {
          val connectionIconRes = if (isConnected) null else CoreRes.drawable.ic_cloud_off
          val textFieldUi = multilineTextFieldUi.copy(connectionIconRes = connectionIconRes)
          copy(multilineTextFieldUi = textFieldUi)
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
      ChatDetailsScreenUiState.mock.copy(
        chatEmptyStateUi = getChatEmptyStateUi(chatId),
        chatHeaderUi = chatDetails?.chat?.toChatHeaderUi(userId),
        chatMessagesUi = chatDetails?.chatMessagesAndMembers?.toUiList(userId),
      )
    }
      .flowOn(Dispatchers.IO)
      .onEach {
        updateUiState {
          val startCurrentId = chatMessagesUi?.firstOrNull()?.id
          val startNewId = it.chatMessagesUi?.firstOrNull()?.id
          val scrollToStart = startCurrentId != startNewId && isNearStart

          copy(
            chatEmptyStateUi = it.chatEmptyStateUi,
            chatHeaderUi = it.chatHeaderUi,
            chatMessagesUi = it.chatMessagesUi,
            scrollToStart = if (scrollToStart) Event(Unit) else null,
          )
        }
      }
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

  private fun observeChatId() {
    _chatId
      .onEach { chatId ->
        paginator = if (chatId == null) {
          null
        } else {
          Paginator(
            // First page
            initialKey = null,
            onRequest = { chatRepository.syncChatMessages(chatId, before = it) },
            getNextKey = { it.minOfOrNull(ChatMessage::createdAt).toString() },
            onLoad = { updateUiState { copy(isPageLoading = it) } },
            onSuccess = { updateUiState { copy(isLastPage = it.size < PAGE_SIZE) } },
            onError = { updateUiState { copy(pageLoadingError = it.toStringRes()) } },
          )
        }
        paginator?.reset()
      }
      .launchIn(viewModelScope)
  }

  fun loadChat(chatId: String?) {
    _chatId.update { chatId }

    if (chatId == null) {
      updateUiState {
        copy(
          chatMessagesUi = null,
          lastChatMessagesCount = 0
        )
      }
      return
    }

    viewModelScope.launch {
      chatRepository
        .syncChat(chatId)
        .onFailure { showSnackbar(it.toStringRes()) }
    }
  }

  fun handleAction(action: ChatDetailsScreenAction) {
    when (action) {
      is ChatDetailsScreenAction.OnMenuClick -> {
        updateUiState { copy(dropDownItemsUi = getChatsDropDownItems()) }
      }
      is ChatDetailsScreenAction.OnMenuDismiss -> {
        updateUiState { copy(dropDownItemsUi = null) }
      }
      is ChatDetailsScreenAction.OnMenuItemClick -> {
        handleMenuItemClick(action.dropDownItemPm)
      }
      is ChatDetailsScreenAction.OnMessageLongClick -> updateUiState {
        copy(longPressedMessageId = action.messageId)
      }
      is ChatDetailsScreenAction.OnMessageMenuDismiss -> updateUiState {
        copy(longPressedMessageId = null)
      }
      is ChatDetailsScreenAction.OnMessageMenuItemClick -> {
        handleMessageMenuItemClick(action.dropDownItemPm)
      }
      is ChatDetailsScreenAction.OnMessageRetryClick -> {
        resendMessage(action.messageId)
      }
      is ChatDetailsScreenAction.OnSendClick -> {
        sendMessage()
      }
      is ChatDetailsScreenAction.OnScroll -> {
        loadNextChatMessages(action.lazyListScrollInfo)
        toggleScrollToStart(action.lazyListScrollInfo)
        toggleStickyDate(action.lazyListScrollInfo)
        toggleIsNearStart(action.lazyListScrollInfo)
      }
      is ChatDetailsScreenAction.OnScrollToStartClick -> updateUiState {
        copy(scrollToStart = Event(Unit))
      }
      is ChatDetailsScreenAction.OnPageRetryClick -> {
        loadNextChatMessages()
      }
      else -> Unit
    }
  }

  private fun loadNextChatMessages(lazyListScrollInfo: LazyListScrollInfo) {
    val lastVisibleItemIndex = lazyListScrollInfo.visibleItemsIndices.lastOrNull() ?: return
    val remainingItemsCount = lazyListScrollInfo.totalItemsCount - lastVisibleItemIndex - 1
    val loadNextItems = remainingItemsCount <= 5 &&
      !screenUiState.value.uiState.isPageLoading &&
      !screenUiState.value.uiState.isLastPage &&
      lazyListScrollInfo.totalItemsCount > screenUiState.value.uiState.lastChatMessagesCount

    if (!loadNextItems) return

    updateUiState { copy(lastChatMessagesCount = lazyListScrollInfo.totalItemsCount) }
    loadNextChatMessages()
  }

  private fun loadNextChatMessages() {
    viewModelScope.launch {
      paginator?.loadNextItems()
    }
  }

  private fun toggleScrollToStart(lazyListScrollInfo: LazyListScrollInfo) {
    val firstVisibleItemIndex = lazyListScrollInfo.visibleItemsIndices.firstOrNull()
    val showScrollToStartButton = firstVisibleItemIndex != null &&
      firstVisibleItemIndex > 2
    updateUiState { copy(showScrollToStartButton = showScrollToStartButton) }
  }

  private fun toggleStickyDate(lazyListScrollInfo: LazyListScrollInfo) {
    viewModelScope.launch {
      val visibleItemsIndices = lazyListScrollInfo.visibleItemsIndices
      val lastVisibleItemIndex = visibleItemsIndices.lastOrNull()
      val isAtStart = visibleItemsIndices.firstOrNull() == 0
      val isAtEnd = lazyListScrollInfo.totalItemsCount == visibleItemsIndices.lastOrNull()?.minus(1)

      val showStickyDate = lazyListScrollInfo.isScrollInProgress &&
        !isAtStart &&
        !isAtEnd &&
        lastVisibleItemIndex != null &&
        lastVisibleItemIndex > 0

      if (showStickyDate) {
        val stickyDate = getStickyDate(lastVisibleItemIndex, lazyListScrollInfo.totalItemsCount)
        updateUiState { copy(stickyDate = stickyDate) }
      } else {
        delay(1.seconds)
        updateUiState { copy(stickyDate = null) }
      }
    }
  }

  private fun getStickyDate(
    lastVisibleItemIndex: Int,
    totalItemsCount: Int,
  ): TextProvider? {
    val dateDividerUi = (lastVisibleItemIndex until totalItemsCount)
      .firstNotNullOfOrNull {
        val chatMessagesUi = screenUiState.value.uiState.chatMessagesUi
        chatMessagesUi?.getOrNull(it) as? ChatMessageUi.DateDividerUi
      }

    return when (val date = dateDividerUi?.date) {
      is TextProvider.Dynamic -> date
      is TextProvider.Resource -> when (date.id) {
        Res.string.today,
        Res.string.yesterday -> null
        else -> date
      }
      else -> null
    }
  }

  private fun toggleIsNearStart(lazyListScrollInfo: LazyListScrollInfo) {
    val firstVisibleItemIndex = lazyListScrollInfo.visibleItemsIndices.firstOrNull()
    val isNearStart = firstVisibleItemIndex != null &&
      firstVisibleItemIndex < 3
    updateUiState { copy(isNearStart = isNearStart) }
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

  private fun handleMenuItemClick(dropDownItemPm: DropDownItemUi) {
    when (dropDownItemPm.titleRes) {
      Res.string.chat_members -> updateUiState {
        copy(openChatManageEvent = Event(_chatId.value!!))
      }
      Res.string.log_out -> {
        leaveChat()
      }
    }
  }

  private fun handleMessageMenuItemClick(dropDownItemPm: DropDownItemUi) {
    when (dropDownItemPm.titleRes) {
      Res.string.delete_for_everyone -> deleteChatMessage(dropDownItemPm.id)
      else -> Unit
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

  private fun deleteChatMessage(messageId: String) {
    viewModelScope.launch {
      chatRepository
        .deleteChatMessage(messageId)
        .onFailure { showSnackbar(it.toStringRes()) }
        .onSuccess {
          updateUiState { copy(longPressedMessageId = null) }
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
        id = Uuid.random().toString(),
        leadingIconRes = CoreRes.drawable.ic_users,
        titleRes = Res.string.chat_members,
        colorToken = ColorToken.TextSecondary,
      ),
      DropDownItemUi(
        id = Uuid.random().toString(),
        leadingIconRes = Res.drawable.ic_leave,
        titleRes = Res.string.leave_the_chat,
        colorToken = ColorToken.TextDestructive,
      ),
    )
  }
}
