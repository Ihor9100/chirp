package com.plcoding.feature.chat.data.datasource.local

import androidx.room.useWriterConnection
import com.plcoding.core.data.tools.dbSafeCall
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.feature.chat.database.ChirpDatabase
import com.plcoding.feature.chat.database.dao.ChatAndMemberDao
import com.plcoding.feature.chat.database.dao.ChatMembersDao
import com.plcoding.feature.chat.database.dao.ChatMessagesDao
import com.plcoding.feature.chat.database.dao.ChatsDao
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.relation.ChatAndMembersAndMessagesRelation
import com.plcoding.feature.chat.database.relation.ChatAndMembersRelation
import kotlinx.coroutines.flow.Flow

class ChatsRoomLocalDataSource(
  private val chirpDatabase: ChirpDatabase,
  private val chatsDao: ChatsDao,
  private val chatMembersDao: ChatMembersDao,
  private val chatMessagesDao: ChatMessagesDao,
  private val chatAndMemberDao: ChatAndMemberDao,
) : ChatsLocalDataSource {

  override suspend fun observeChatMembers(chatId: String): Flow<List<ChatMemberEntity>> {
    return chatMembersDao.observe(chatId)
  }

  override suspend fun observeChatAndMembers(): Flow<List<ChatAndMembersRelation>> {
    return chatsDao.subscribeToChatsAndMembers()
  }

  override suspend fun observeChatAndMembersAndMessages(chatId: String): Flow<ChatAndMembersAndMessagesRelation?> {
    return chatsDao.observeChatAndMembersAndMessages(chatId)
  }

  override suspend fun upsertChatDetails(
    chat: ChatEntity,
    chatMembers: List<ChatMemberEntity>,
    chatMessages: List<ChatMessageEntity>,
    chatsAndMembers: List<ChatAndMemberEntity>
  ): Empty<DataError.Local> {
    return dbSafeCall {
      chirpDatabase.useWriterConnection {
        chatsDao.upsert(chat)
        chatMembersDao.upsert(chatMembers)
        chatMessagesDao.upsert(chatMessages)
        chatAndMemberDao.upsert(chatsAndMembers)
      }
    }
  }

  override suspend fun replaceChatsDetails(
    chats: List<ChatEntity>,
    chatMembers: List<ChatMemberEntity>,
    chatMessages: List<ChatMessageEntity>,
    chatsAndMembers: List<ChatAndMemberEntity>
  ): Empty<DataError.Local> {
    return dbSafeCall {
      chirpDatabase.useWriterConnection {
        chatsDao.replace(chats)
        chatMembersDao.replace(chatMembers)
        chatMessagesDao.replace(chatMessages)
        chatAndMemberDao.replace(chatsAndMembers)
      }
    }
  }

  override suspend fun removeChatDetails(chatId: String): Empty<DataError.Local> {
    return dbSafeCall {
      chatsDao.delete(chatId)
    }
  }
}