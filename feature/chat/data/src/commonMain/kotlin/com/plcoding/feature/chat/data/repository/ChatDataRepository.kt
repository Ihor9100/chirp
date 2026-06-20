package com.plcoding.feature.chat.data.repository

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.flatMap
import com.plcoding.core.domain.result.map
import com.plcoding.feature.chat.data.datasource.local.ChatsLocalDataSource
import com.plcoding.feature.chat.data.datasource.remote.ChatsRemoteDataSource
import com.plcoding.feature.chat.data.mapper.ChatAndMembersRelationMapper
import com.plcoding.feature.chat.data.mapper.ChatEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberAmMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMessageEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatsAndMembersEntityMapper
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatDataRepository(
  private val localDataSource: ChatsLocalDataSource,
  private val remoteDataSource: ChatsRemoteDataSource,
  private val chatMapper: ChatMapper,
  private val chatMemberAmMapper: ChatMemberAmMapper,
  private val chatAndMembersRelationMapper: ChatAndMembersRelationMapper,
  private val chatEntityMapper: ChatEntityMapper,
  private val chatMemberEntityMapper: ChatMemberEntityMapper,
  private val chatMessageEntityMapper: ChatMessageEntityMapper,
  private val chatsAndMembersEntityMapper: ChatsAndMembersEntityMapper,
) : ChatRepository {

  override suspend fun searchMember(query: String): Result<ChatMember, DataError.Remote> {
    return remoteDataSource
      .searchMember(query)
      .map { chatMemberAmMapper.map(it, Unit) }
  }

  override suspend fun getChat(chatId: String): Result<Chat, DataError.Remote> {
    return remoteDataSource
      .getChat(chatId)
      .map { chatMapper.map(it, Unit) }
  }

  override suspend fun createChat(memberIds: List<String>): Result<Chat, DataError.Remote> {
    return remoteDataSource
      .createChat(memberIds)
      .map { chatMapper.map(it, Unit) }
  }

  override suspend fun observeChats(): Flow<List<Chat>> {
    return localDataSource
      .observeChatAndMembers()
      .map { chatAndMembersRelationMapper.mapList(it, Unit) }
  }

  override suspend fun observeChatDetails(chatId: String): Flow<ChatDetails> {
    return localDataSource
      .observeChatAndMembersAndMessages()
      .map {  }
  }

  override suspend fun syncChats(): Empty<DataError> {
    return remoteDataSource
      .getChats()
      .map { chatMapper.mapList(it, Unit) }
      .flatMap {
        localDataSource.saveChatsDetails(
          chatEntityMapper.mapList(it, Unit),
          chatMemberEntityMapper.mapList(it.flatMap(Chat::members), Unit),
          chatMessageEntityMapper.mapList(it.mapNotNull(Chat::lastMessage), Unit),
          chatsAndMembersEntityMapper.map(it, Unit),
        )
      }
  }
}