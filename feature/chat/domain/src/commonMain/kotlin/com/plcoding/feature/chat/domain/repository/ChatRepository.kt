package com.plcoding.feature.chat.domain.repository

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.model.ChatMember
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

  suspend fun searchMember(query: String): Result<ChatMember, DataError.Remote>

  suspend fun getChat(chatId: String): Result<Chat, DataError.Remote>

  suspend fun createChat(memberIds: List<String>): Result<Chat, DataError.Remote>

  suspend fun observeChats(): Flow<List<Chat>>

  suspend fun observeChatDetails(chatId: String): Flow<ChatDetails>

  suspend fun syncChats(): Empty<DataError>
}