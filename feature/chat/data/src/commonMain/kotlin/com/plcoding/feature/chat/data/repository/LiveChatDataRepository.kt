package com.plcoding.feature.chat.data.repository

import com.plcoding.core.data.tools.dbSafeCall
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.getOrNull
import com.plcoding.core.domain.result.onFailure
import com.plcoding.feature.chat.data.datasource.local.ChatsLocalDataSource
import com.plcoding.feature.chat.data.mapper.toDomain
import com.plcoding.feature.chat.data.mapper.toEntity
import com.plcoding.feature.chat.data.mapper.toOutgoingMessageDto
import com.plcoding.feature.chat.data.model.WebSocketMessageDto
import com.plcoding.feature.chat.data.model.WebSocketMessageType
import com.plcoding.feature.chat.data.model.WebSocketPayloadDto
import com.plcoding.feature.chat.data.network.KtorWebSocketConnector
import com.plcoding.feature.chat.database.dao.ChatMessagesDao
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.domain.repository.LiveChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

class LiveChatDataRepository(
  private val json: Json,
  private val coroutineScope: CoroutineScope,
  private val ktorWebSocketConnector: KtorWebSocketConnector,
  private val chatMessagesDao: ChatMessagesDao,
  private val chatRepository: ChatRepository,
  private val localDataSource: ChatsLocalDataSource,
  private val preferencesRepository: PreferencesRepository,
) : LiveChatRepository {

  override val chatMessage = ktorWebSocketConnector
    .webSocketMessagesDto
    .map(::getWebSocketPayloadDto)
    .onEach(::handleWebSocketPayloadDto)
    .filterIsInstance<WebSocketPayloadDto.IncomingMessageDto>()
    .map { it.toDomain() }
    .shareIn(coroutineScope, SharingStarted.WhileSubscribed(5.seconds))

  override val connectionState = ktorWebSocketConnector.connectionState

  override suspend fun sendMessage(chatMessage: ChatMessage): Empty<DataError> {
    return dbSafeCall {
      val authInfo = preferencesRepository.observeAuthInfo().firstOrNull()
        ?: return Result.Failure(DataError.Local.NOT_FOUND)

      val chatMessageEntity = chatMessage.toEntity(authInfo.user.id)
      chatMessagesDao.upsert(chatMessageEntity)

      val outgoingMessageDto = chatMessage.toOutgoingMessageDto()
      val webSocketMessageDto = WebSocketMessageDto.from(outgoingMessageDto, json)
      val rawWebSocketMessage = json.encodeToString(webSocketMessageDto)

      return ktorWebSocketConnector
        .sendMessage(rawWebSocketMessage)
        .onFailure {
          coroutineScope.launch {
            val chatMessageEntity =
              chatMessageEntity.copy(status = ChatMessageDeliveryStatus.FAILED.name)
            chatMessagesDao.upsert(chatMessageEntity)
          }.join()
        }
    }
  }

  private fun getWebSocketPayloadDto(webSocketMessageDto: WebSocketMessageDto): WebSocketPayloadDto {
    return with(webSocketMessageDto) {
      when (WebSocketMessageType.valueOf(type)) {
        WebSocketMessageType.NEW_MESSAGE -> {
          json.decodeFromString<WebSocketPayloadDto.IncomingMessageDto>(payload)
        }
        WebSocketMessageType.MESSAGE_DELETED -> {
          json.decodeFromString<WebSocketPayloadDto.MessageDeletedDto>(payload)
        }
        WebSocketMessageType.PROFILE_PICTURE_UPDATED -> {
          json.decodeFromString<WebSocketPayloadDto.ProfilePictureUpdatedDto>(payload)
        }
        WebSocketMessageType.CHAT_PARTICIPANTS_CHANGED -> {
          json.decodeFromString<WebSocketPayloadDto.ChatMembersChangedDto>(payload)
        }
      }
    }
  }

  private suspend fun handleWebSocketPayloadDto(payloadDto: WebSocketPayloadDto) {
    when (payloadDto) {
      is WebSocketPayloadDto.ChatMembersChangedDto -> chatRepository.syncChat(payloadDto.chatId)
      is WebSocketPayloadDto.MessageDeletedDto -> localDataSource.deleteChatMessage(payloadDto.chatId)
      is WebSocketPayloadDto.IncomingMessageDto -> handleNewMessageDto(payloadDto)
      is WebSocketPayloadDto.ProfilePictureUpdatedDto -> handleProfilePictureUpdatedDto(payloadDto)
      else -> Unit
    }
  }

  private suspend fun handleNewMessageDto(incomingMessageDto: WebSocketPayloadDto.IncomingMessageDto) {
    val hasChat = localDataSource.hasChat(incomingMessageDto.chatId).getOrNull()
    if (hasChat != true) {
      chatRepository.syncChat(incomingMessageDto.chatId)
    }

    localDataSource.upsertChatMessage(incomingMessageDto.toEntity())
  }

  private suspend fun handleProfilePictureUpdatedDto(
    profilePictureUpdatedDto: WebSocketPayloadDto.ProfilePictureUpdatedDto,
  ) {
    localDataSource.updateChatMember(
      id = profilePictureUpdatedDto.userId,
      avatarUrl = profilePictureUpdatedDto.newUrl,
    )

    val authInfo = preferencesRepository.observeAuthInfo().firstOrNull()
    if (authInfo?.user?.id == profilePictureUpdatedDto.userId) {
      val user = authInfo.user.copy(profilePictureUrl = profilePictureUpdatedDto.newUrl)
      preferencesRepository.saveAuthInfo(authInfo.copy(user = user))
    }
  }
}