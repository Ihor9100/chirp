package com.plcoding.feature.chat.domain.repository

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.model.ChatMember
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

  // Local
  suspend fun observeChats(): Flow<List<Chat>>
  suspend fun observeChatDetails(chatId: String): Flow<ChatDetails?>

  // Remote
  suspend fun searchMember(query: String): Result<ChatMember, DataError.Remote>
  suspend fun createChat(memberIds: List<String>): Empty<DataError>
  suspend fun syncChat(chatId: String): Empty<DataError>
  suspend fun syncChats(): Empty<DataError>
  suspend fun leaveChat(chatId: String): Empty<DataError>
}