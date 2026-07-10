package com.plcoding.feature.chat.data.repository

import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.onFailure
import com.plcoding.feature.chat.data.mapper.NewMessageAmMapper
import com.plcoding.feature.chat.data.mapper.WebSocketMessageAmMapper
import com.plcoding.feature.chat.data.network.KtorWebSocketConnector
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import com.plcoding.feature.chat.domain.model.ConnectionError
import com.plcoding.feature.chat.domain.model.ConnectionState
import com.plcoding.feature.chat.domain.repository.ChatRepository
import com.plcoding.feature.chat.domain.repository.LiveChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class LiveChatDataRepository(
  private val json: Json,
  private val ktorWebSocketConnector: KtorWebSocketConnector,
  private val chatRepository: ChatRepository,
  private val newMessageAmMapper: NewMessageAmMapper,
  private val webSocketMessageAmMapper: WebSocketMessageAmMapper,
) : LiveChatRepository {

  override val chatMessage: Flow<ChatMessage>
    get() = TODO("Not yet implemented")
  override val connectionState: Flow<ConnectionState>
    get() = TODO("Not yet implemented")

  override suspend fun sendMessage(chatMessage: ChatMessage): Empty<ConnectionError> {
    val newMessageAm = newMessageAmMapper.map(chatMessage)
    val webSocketMessageAm = webSocketMessageAmMapper.map(newMessageAm)
    val message = json.encodeToString(webSocketMessageAm)

    return ktorWebSocketConnector
      .sendMessage(message)
      .onFailure {
        val ddd =chatRepository.updateChatMessage(
          chatMessage.id,
          ChatMessageDeliveryStatus.FAILED,
        )
      }
  }
}