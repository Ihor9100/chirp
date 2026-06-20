package com.plcoding.feature.chat.data.datasource.local

import com.plcoding.core.data.tools.dbSafeCall
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
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
import com.plcoding.feature.chat.domain.model.ChatDetails
import com.plcoding.feature.chat.domain.model.ChatMember
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatsRoomLocalDataSource(
  private val chatsDao: ChatsDao,
  private val chatMembersDao: ChatMembersDao,
  private val chatMessagesDao: ChatMessagesDao,
  private val chatAndMemberDao: ChatAndMemberDao,
  private val chatEntityMapper: ChatEntityMapper,
  private val chatMemberEntityMapper: ChatMemberEntityMapper,
  private val chatMessageEntityMapper: ChatMessageEntityMapper,
  private val chatAndMembersRelationMapper: ChatAndMembersRelationMapper,
  private val chatsAndMembersEntityMapper: ChatsAndMembersEntityMapper,
) : ChatsLocalDataSource {

  override suspend fun observeChats(): Flow<List<Chat>> {
    return chatsDao
      .subscribeToChatsAndMembers()
      .map { chatAndMembersRelationMapper.mapList(it, Unit) }
  }

  override suspend fun observeChatDetails(chatId: String): Flow<ChatDetails> {
    return chatsDao
      .observeChatAndMembersAndMessages(chatId)
      .map {  }
  }

  override suspend fun saveChats(chats: List<Chat>): Empty<DataError.Local> {
    return dbSafeCall {
      chatsDao.replace(chatEntityMapper.mapList(chats, Unit))
      chatMembersDao.replace(chatMemberEntityMapper.mapList(chats.flatMap(Chat::members), Unit))
      chatMessagesDao.replace(chatMessageEntityMapper.mapList(chats.mapNotNull(Chat::lastMessage), Unit))
      chatAndMemberDao.replace(chatsAndMembersEntityMapper.map(chats, Unit))
    }
  }
}