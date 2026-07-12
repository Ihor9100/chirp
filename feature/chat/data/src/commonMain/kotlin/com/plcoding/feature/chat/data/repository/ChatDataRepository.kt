package com.plcoding.feature.chat.data.repository

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.flatMap
import com.plcoding.core.domain.result.map
import com.plcoding.feature.chat.data.datasource.local.ChatsLocalDataSource
import com.plcoding.feature.chat.data.datasource.remote.ChatsRemoteDataSource
import com.plcoding.feature.chat.data.mapper.toDomain
import com.plcoding.feature.chat.data.mapper.toEntities
import com.plcoding.feature.chat.data.mapper.toEntity
import com.plcoding.feature.chat.data.model.ChatAm
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatDataRepository(
  private val localDataSource: ChatsLocalDataSource,
  private val remoteDataSource: ChatsRemoteDataSource,
) : ChatRepository {

  override fun observeChats(): Flow<List<Chat>> {
    return localDataSource
      .observeChatAndMembers()
      .map { entities -> entities.map { it.toDomain() } }
  }

  override fun observeChatDetails(chatId: String): Flow<ChatDetails?> {
    return localDataSource
      .observeChatAndMembersAndMessages(chatId)
      .map { it?.toDomain() }
  }

  override fun observeChatMembers(chatId: String): Flow<List<ChatMember>> {
    return localDataSource
      .observeChatMembers(chatId)
      .map { entities -> entities.map { it.toDomain() } }
  }

  override suspend fun searchMember(query: String): Result<ChatMember, DataError.Remote> {
    return remoteDataSource
      .searchMember(query)
      .map { it.toDomain() }
  }

  override suspend fun createChat(memberIds: List<String>): Empty<DataError> {
    return remoteDataSource
      .createChat(memberIds)
      .flatMap { upsertChatDetails(it) }
  }

  override suspend fun syncChat(chatId: String): Empty<DataError> {
    return remoteDataSource
      .getChat(chatId)
      .flatMap { upsertChatDetails(it) }
  }

  override suspend fun syncChats(): Empty<DataError> {
    return remoteDataSource
      .getChats()
      .flatMap { ams ->
        localDataSource.replaceChatsDetails(
          chats = ams.map { it.toEntity() },
          chatMembers = ams.flatMap { it.participants }.map { it.toEntity() },
          chatMessages = ams.mapNotNull { it.lastMessage }.map { it.toEntity() },
          chatsAndMembers = ams.flatMap { it.toEntities() },
        )
      }
  }

  override suspend fun leaveChat(chatId: String): Empty<DataError> {
    return remoteDataSource
      .leaveChat(chatId)
      .flatMap { localDataSource.removeChatDetails(chatId) }
  }

  override suspend fun addChatMembers(
    chatId: String,
    memberIds: List<String>,
  ): Empty<DataError> {
    return remoteDataSource
      .addChatMembers(chatId, memberIds)
      .flatMap { upsertChatDetails(it) }
  }

  private suspend fun upsertChatDetails(chatAm: ChatAm): Empty<DataError> {
    return localDataSource.upsertChatDetails(
      chatAm.toEntity(),
      chatAm.participants.map { it.toEntity() },
      listOfNotNull(chatAm.lastMessage).map { it.toEntity() },
      chatAm.toEntities(),
    )
  }
}
