package com.plcoding.feature.chat.domain.repository

import com.plcoding.core.domain.result.Empty
import com.plcoding.feature.chat.domain.model.ChatMessage
import com.plcoding.feature.chat.domain.model.ConnectionError
import com.plcoding.feature.chat.domain.model.ConnectionState
import kotlinx.coroutines.flow.Flow

interface LiveChatRepository {
  val chatMessage: Flow<ChatMessage>
  val connectionState: Flow<ConnectionState>
  suspend fun sendMessage(chatMessage: ChatMessage): Empty<ConnectionError>
}