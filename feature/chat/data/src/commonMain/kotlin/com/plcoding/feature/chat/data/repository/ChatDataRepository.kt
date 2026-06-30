package com.plcoding.feature.chat.data.repository

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.flatMap
import com.plcoding.core.domain.result.map
import com.plcoding.feature.chat.data.datasource.local.ChatsLocalDataSource
import com.plcoding.feature.chat.data.datasource.remote.ChatsRemoteDataSource
import com.plcoding.feature.chat.data.mapper.ChatAndMembersRelationMapper
import com.plcoding.feature.chat.data.mapper.ChatDetailsMapper
import com.plcoding.feature.chat.data.mapper.ChatEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberAmMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMessageEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatsAndMembersEntityMapper
import com.plcoding.feature.chat.data.model.ChatAm
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.flatMap

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
  private val chatDetailsMapper: ChatDetailsMapper,
) : ChatRepository {

  override suspend fun observeChats(): Flow<List<Chat>> {
    return localDataSource
      .observeChatAndMembers()
      .map(chatAndMembersRelationMapper::mapList)
  }

  override suspend fun observeChatDetails(chatId: String): Flow<ChatDetails> {
    return localDataSource
      .observeChatAndMembersAndMessages(chatId)
      .map(chatDetailsMapper::map)
  }

  override suspend fun searchMember(query: String): Result<ChatMember, DataError.Remote> {
    return remoteDataSource
      .searchMember(query)
      .map(chatMemberAmMapper::map)
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

  private suspend fun upsertChatDetails(chatAm: ChatAm): Empty<DataError> {
    val chat = chatMapper.map(chatAm)

    return localDataSource.upsertChatDetails(
      chatEntityMapper.map(chat, ChatEntityMapper.Params(listOf(), null)),
      chatMemberEntityMapper.mapList(chat.members),
      chatMessageEntityMapper.mapList(listOfNotNull(chat.lastMessage)),
      chatsAndMembersEntityMapper.map(listOf(chat)),
    )
  }

  override suspend fun syncChats(): Empty<DataError> {
    return remoteDataSource
      .getChats()
      .flatMap {
        val chats = chatMapper.mapList(it)

        localDataSource.replaceChatsDetails(
          chatEntityMapper.mapList(chats, ChatEntityMapper.Params(listOf(), null)),
          chatMemberEntityMapper.mapList(chats.flatMap(Chat::members)),
          chatMessageEntityMapper.mapList(chats.mapNotNull(Chat::lastMessage)),
          chatsAndMembersEntityMapper.map(chats),
        )
      }
  }

  override suspend fun leaveChat(chatId: String): Empty<DataError> {
    return remoteDataSource
      .leaveChat(chatId)
      .flatMap { localDataSource.removeChatDetails(chatId) }
  }
}