package com.plcoding.feature.chat.data.repository

import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.getOrNull
import com.plcoding.core.domain.result.onFailure
import com.plcoding.feature.chat.data.datasource.local.ChatsLocalDataSource
import com.plcoding.feature.chat.data.mapper.ChatMessageEntityMapper
import com.plcoding.feature.chat.data.mapper.NewMessageAmMapper
import com.plcoding.feature.chat.data.mapper.WebSocketMessageAmMapper
import com.plcoding.feature.chat.data.model.WebSocketMessageAm
import com.plcoding.feature.chat.data.model.WebSocketMessageTypeAm
import com.plcoding.feature.chat.data.model.WebSocketPayloadAm
import com.plcoding.feature.chat.data.network.KtorWebSocketConnector
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import com.plcoding.feature.chat.domain.model.ConnectionError
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.domain.repository.LiveChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.json.Json

class LiveChatDataRepository(
  private val json: Json,
  private val ktorWebSocketConnector: KtorWebSocketConnector,
  private val chatRepository: ChatRepository,
  private val localDataSource: ChatsLocalDataSource,
  private val newMessageAmMapper: NewMessageAmMapper,
  private val chatMessageEntityMapper: ChatMessageEntityMapper,
  private val webSocketMessageAmMapper: WebSocketMessageAmMapper,
) : LiveChatRepository {

  override val chatMessage = ktorWebSocketConnector
    .webSocketMessagesAm
    .mapNotNull(::getWebSocketPayloadAm)
    .onEach { }

  override val connectionState = ktorWebSocketConnector.connectionState

  override suspend fun sendMessage(chatMessage: ChatMessage): Empty<ConnectionError> {
    val newMessageAm = newMessageAmMapper.map(chatMessage)
    val webSocketMessageAm = webSocketMessageAmMapper.map(newMessageAm)
    val message = json.encodeToString(webSocketMessageAm)

    return ktorWebSocketConnector
      .sendMessage(message)
      .onFailure {
        localDataSource.updateChatMessage(
          chatMessage.id,
          ChatMessageDeliveryStatus.FAILED,
        )
      }
  }

  private fun getWebSocketPayloadAm(webSocketMessageAm: WebSocketMessageAm): WebSocketPayloadAm? {
    return with(webSocketMessageAm) {
      when (WebSocketMessageTypeAm.valueOf(type)) {
        WebSocketMessageTypeAm.NEW_MESSAGE -> {
          json.decodeFromString<WebSocketPayloadAm.NewMessageAm>(payload)
        }
        WebSocketMessageTypeAm.MESSAGE_DELETED -> {
          json.decodeFromString<WebSocketPayloadAm.MessageDeletedAm>(payload)
        }
        WebSocketMessageTypeAm.PROFILE_PICTURE_UPDATED -> {
          json.decodeFromString<WebSocketPayloadAm.ProfilePictureUpdatedAm>(payload)
        }
        WebSocketMessageTypeAm.CHAT_PARTICIPANTS_CHANGED -> {
          json.decodeFromString<WebSocketPayloadAm.ChatMembersChangedAm>(payload)
        }
      }
    }
  }

  private suspend fun getWebSocketPayloadAm(payloadAm: WebSocketPayloadAm) {
    when (payloadAm) {
      is WebSocketPayloadAm.ChatMembersChangedAm -> chatRepository.syncChat(payloadAm.chatId)
      is WebSocketPayloadAm.MessageDeletedAm -> localDataSource.deleteChatMessage(payloadAm.chatId)
      is WebSocketPayloadAm.NewMessageAm -> handleNewMessageAm(payloadAm)
      is WebSocketPayloadAm.ProfilePictureUpdatedAm -> {}
    }
  }

  private suspend fun handleNewMessageAm(newMessageAm: WebSocketPayloadAm.NewMessageAm) {
    val hasChat = localDataSource.hasChat(newMessageAm.chatId).getOrNull()
    if (hasChat != true) {
      chatRepository.syncChat(newMessageAm.chatId)
    }

    val chatMessage = newMessageAmMapper.reverse(newMessageAm)
    val entity = chatMessageEntityMapper.map(chatMessage)
    localDataSource.upsertChatMessage(entity)
  }
}