package com.plcoding.feature.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.relation.ChatAndMembersAndMessagesRelation
import com.plcoding.feature.chat.database.relation.ChatAndMembersRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatsDao {

  @Upsert
  suspend fun upsert(entity: ChatEntity)

  @Upsert
  suspend fun upsert(entities: List<ChatEntity>)

  @Transaction
  suspend fun replace(entities: List<ChatEntity>) {
    deleteAll()
    upsert(entities)
  }

  @Query("SELECT * FROM chats WHERE id = :id")
  fun get(id: String): ChatEntity?

  @Query("SELECT id FROM chats")
  fun getIds(): List<String>

  @Query("SELECT COUNT(*) FROM chats")
  fun getCount(): Flow<Int>

  @Query("SELECT * FROM chats WHERE id = :id")
  fun getChatAndMembersAndMessages(id: String): ChatAndMembersAndMessagesRelation?

  @Query("SELECT * FROM chats")
  fun subscribeToChatsAndMembers(): Flow<List<ChatAndMembersRelation>>

  @Query("DELETE FROM chats WHERE id = :id")
  suspend fun delete(id: String)

  @Query("DELETE FROM chats WHERE id IN (:ids)")
  suspend fun delete(ids: List<String>)

  @Query("DELETE FROM chats")
  suspend fun deleteAll()
}