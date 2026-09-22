package com.plcoding.feature.chat.data.datasource.local

import androidx.room.useWriterConnection
import com.plcoding.core.data.tools.dbSafeCall
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.database.ChirpDatabase
import com.plcoding.feature.chat.database.dao.ChatAndMemberDao
import com.plcoding.feature.chat.database.dao.ChatMessageAttachmentsDao
import com.plcoding.feature.chat.database.dao.ChatMembersDao
import com.plcoding.feature.chat.database.dao.ChatMessagesDao
import com.plcoding.feature.chat.database.dao.ChatsDao
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMessageAttachmentEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.relation.ChatAndMembersAndMessagesRelation
import com.plcoding.feature.chat.database.relation.ChatAndMembersRelation
import com.plcoding.feature.chat.database.relation.ChatMessageAndMemberRelation
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock

class ChatsRoomLocalDataSource(
  private val chirpDatabase: ChirpDatabase,
  private val chatsDao: ChatsDao,
  private val chatMembersDao: ChatMembersDao,
  private val chatMessagesDao: ChatMessagesDao,
  private val chatMessageAttachmentsDao: ChatMessageAttachmentsDao,
  private val chatAndMemberDao: ChatAndMemberDao,
) : ChatsLocalDataSource {

  override fun observeChatMembers(chatId: String): Flow<List<ChatMemberEntity>> {
    return chatMembersDao.observe(chatId)
  }

  override fun observeChatMessages(chatId: String): Flow<List<ChatMessageAndMemberRelation>> {
    return chatMessagesDao.observe(chatId)
  }

  override fun observeChatAndMembers(): Flow<List<ChatAndMembersRelation>> {
    return chatsDao.observeChatsAndMembers()
  }

  override fun observeChatAndMembersAndMessages(chatId: String): Flow<ChatAndMembersAndMessagesRelation?> {
    return chatsDao.observeChatAndMembersAndMessages(chatId)
  }

  override suspend fun updateChatMember(id: String, avatarUrl: String?): Empty<DataError.Local> {
    return dbSafeCall {
      chatMembersDao.update(id, avatarUrl)
    }
  }

  override suspend fun upsertChatMessage(
    entity: ChatMessageEntity,
    attachments: List<ChatMessageAttachmentEntity>,
  ): Empty<DataError.Local> {
    return dbSafeCall {
      chirpDatabase.useWriterConnection {
        chatMessagesDao.upsert(entity)
        chatMessageAttachmentsDao.deleteForMessages(listOf(entity.id))
        chatMessageAttachmentsDao.upsert(attachments)
      }
    }
  }

  override suspend fun upsertChatMessages(
    entities: List<ChatMessageEntity>,
    attachments: List<ChatMessageAttachmentEntity>,
  ): Empty<DataError.Local> {
    return dbSafeCall {
      chirpDatabase.useWriterConnection {
        chatMessagesDao.upsert(entities)
        chatMessageAttachmentsDao.deleteForMessages(entities.map { it.id })
        chatMessageAttachmentsDao.upsert(attachments)
      }
    }
  }

  override suspend fun updateChatMessage(id: String, deliveryStatus: ChatMessageDeliveryStatus) {
    chatMessagesDao.update(
      id,
      Clock.System.now().toEpochMilliseconds(),
      deliveryStatus.name,
    )
  }

  override suspend fun deleteChats() {
    chatsDao.deleteAll()
  }

  override suspend fun deleteChatMessage(id: String) {
    chatMessagesDao.delete(id)
  }

  override suspend fun replaceChatMessages(
    chatId: String,
    entities: List<ChatMessageEntity>,
    attachments: List<ChatMessageAttachmentEntity>,
  ): Empty<DataError.Local> {
    return dbSafeCall {
      chirpDatabase.useWriterConnection {
        chatMessageAttachmentsDao.deleteForChat(chatId)
        chatMessagesDao.replace(chatId, entities)
        chatMessageAttachmentsDao.upsert(attachments)
      }
    }
  }

  override suspend fun hasChat(id: String): Result<Boolean, DataError.Local> {
    return dbSafeCall {
      chatsDao.hasChat(id)
    }
  }

  override suspend fun getChatMessage(id: String): ChatMessageEntity? {
    return chatMessagesDao.get(id)
  }

  override suspend fun upsertChatDetails(
    chat: ChatEntity,
    chatMembers: List<ChatMemberEntity>,
    chatMessages: List<ChatMessageEntity>,
    chatMessageAttachments: List<ChatMessageAttachmentEntity>,
    chatsAndMembers: List<ChatAndMemberEntity>
  ): Empty<DataError.Local> {
    return dbSafeCall {
      chirpDatabase.useWriterConnection {
        chatsDao.upsert(chat)
        chatMembersDao.upsert(chatMembers)
        chatMessagesDao.upsert(chatMessages)
        chatMessageAttachmentsDao.deleteForMessages(chatMessages.map { it.id })
        chatMessageAttachmentsDao.upsert(chatMessageAttachments)
        chatAndMemberDao.upsert(chatsAndMembers)
      }
    }
  }

  override suspend fun replaceChatsDetails(
    chats: List<ChatEntity>,
    chatMembers: List<ChatMemberEntity>,
    chatMessages: List<ChatMessageEntity>,
    chatMessageAttachments: List<ChatMessageAttachmentEntity>,
    chatsAndMembers: List<ChatAndMemberEntity>
  ): Empty<DataError.Local> {
    return dbSafeCall {
      chirpDatabase.useWriterConnection {
        chatsDao.replace(chats)
        chatMembersDao.replace(chatMembers)
        chatMessagesDao.replace(chatMessages)
        chatMessageAttachmentsDao.deleteAll()
        chatMessageAttachmentsDao.upsert(chatMessageAttachments)
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
