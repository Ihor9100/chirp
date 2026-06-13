package com.plcoding.feature.chat.data.repository

import com.plcoding.core.data.tools.get
import com.plcoding.core.data.tools.post
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.map
import com.plcoding.core.domain.result.onSuccess
import com.plcoding.feature.chat.data.mapper.ChatAndMembersRelationMapper
import com.plcoding.feature.chat.data.mapper.ChatEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberAmMapper
import com.plcoding.feature.chat.data.mapper.ChatMessageEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatsAndMembersEntityMapper
import com.plcoding.feature.chat.data.model.ChatAm
import com.plcoding.feature.chat.data.model.ChatCreateRequestAm
import com.plcoding.feature.chat.data.model.ChatMemberAm
import com.plcoding.feature.chat.database.dao.ChatAndMemberDao
import com.plcoding.feature.chat.database.dao.ChatMembersDao
import com.plcoding.feature.chat.database.dao.ChatMessagesDao
import com.plcoding.feature.chat.database.dao.ChatsDao
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.repository.ChatRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatDataRepository(
  private val httpClient: HttpClient,
  private val chatMemberAmMapper: ChatMemberAmMapper,
  private val chatMapper: ChatMapper,
  private val chatsDao: ChatsDao,
  private val chatMembersDao: ChatMembersDao,
  private val chatMessagesDao: ChatMessagesDao,
  private val chatAndMemberDao: ChatAndMemberDao,
  private val chatEntityMapper: ChatEntityMapper,
  private val chatMemberEntityMapper: ChatMemberEntityMapper,
  private val chatMessageEntityMapper: ChatMessageEntityMapper,
  private val chatAndMembersRelationMapper: ChatAndMembersRelationMapper,
  private val chatsAndMembersEntityMapper: ChatsAndMembersEntityMapper,
) : ChatRepository {

  override suspend fun searchMember(query: String): Result<ChatMember, DataError.Remote> {
    return httpClient.get<ChatMemberAm>(
      route = "/participants",
      params = mapOf("query" to query),
    ).map {
      chatMemberAmMapper.map(it, Unit)
    }
  }

  override suspend fun createChat(memberIds: List<String>): Result<Chat, DataError.Remote> {
    return httpClient.post<ChatCreateRequestAm, ChatAm>(
      route = "/chat",
      request = ChatCreateRequestAm(memberIds)
    ).map {
      chatMapper.map(it, Unit)
    }
  }

  override suspend fun observeChats(): Flow<List<Chat>> {
    return chatsDao
      .subscribeToChatsAndMembers()
      .map { chatAndMembersRelationMapper.mapList(it, Unit) }
  }

  override suspend fun getChats(): Result<List<Chat>, DataError.Remote> {
    // TODO: extract to dataSource
    //  httpClient.get<List<ChatAm>>(
    //      route = "/chat"
    //    ).map {
    //      chatMapper.map(it, Unit)
    //    }
    return httpClient.get<List<ChatAm>>(
      route = "/chat"
    ).map {
      chatMapper.mapList(it, Unit)
    }.onSuccess {
      chatsDao.replace(chatEntityMapper.mapList(it, Unit))
      chatMembersDao.replace(chatMemberEntityMapper.mapList(it.flatMap(Chat::members), Unit))
      chatMessagesDao.replace(chatMessageEntityMapper.mapList(it.mapNotNull(Chat::lastMessage), Unit))
      chatAndMemberDao.replace(chatsAndMembersEntityMapper.map(it, Unit))
    }
  }
}