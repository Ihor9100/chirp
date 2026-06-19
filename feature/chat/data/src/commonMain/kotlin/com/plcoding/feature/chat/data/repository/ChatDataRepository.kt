package com.plcoding.feature.chat.data.repository

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.asEmpty
import com.plcoding.core.domain.result.flatMap
import com.plcoding.core.domain.result.map
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.feature.chat.data.datasource.local.ChatsLocalDataSource
import com.plcoding.feature.chat.data.datasource.remote.ChatsRemoteDataSource
import com.plcoding.feature.chat.data.mapper.ChatAndMembersRelationMapper
import com.plcoding.feature.chat.data.mapper.ChatEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMessageEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatsAndMembersEntityMapper
import com.plcoding.feature.chat.database.dao.ChatAndMemberDao
import com.plcoding.feature.chat.database.dao.ChatMembersDao
import com.plcoding.feature.chat.database.dao.ChatMessagesDao
import com.plcoding.feature.chat.database.dao.ChatsDao
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatDataRepository(
  private val localDataSource: ChatsLocalDataSource,
  private val remoteDataSource: ChatsRemoteDataSource,
) : ChatRepository {

  override suspend fun searchMember(query: String): Result<ChatMember, DataError.Remote> {
    return remoteDataSource.searchMember(query)
  }

  override suspend fun getChat(chatId: String): Result<Chat, DataError.Remote> {
    return remoteDataSource.getChat(chatId)
  }

  override suspend fun createChat(memberIds: List<String>): Result<Chat, DataError.Remote> {
    return remoteDataSource.createChat(memberIds)
  }

  override suspend fun observeChats(): Flow<List<Chat>> {
    return localDataSource.observeChats()
  }

  override suspend fun syncChats(): Empty<DataError> {
    return remoteDataSource
      .getChats()
      .flatMap { localDataSource.saveChats(it) }
  }
}