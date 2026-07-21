package com.plcoding.feature.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.relation.ChatMessageAndMemberRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessagesDao {

  @Upsert
  suspend fun upsert(entity: ChatMessageEntity)

  @Upsert
  suspend fun upsert(entities: List<ChatMessageEntity>)

  @Transaction
  suspend fun replace(entities: List<ChatMessageEntity>) {
    deleteAll()
    upsert(entities)
  }

  @Query(
    """
      UPDATE chat_messages
      SET status = :deliveryStatus, timestamp =:timestamp
      WHERE id = :id
    """
  )
  suspend fun update(id: String, timestamp: Long, deliveryStatus: String)

  @Query("SELECT * FROM chat_messages WHERE id = :id")
  suspend fun get(id: String): ChatMessageEntity?

  @Query("SELECT * FROM chat_messages WHERE chatId = :chatId ORDER BY timestamp DESC")
  fun observe(chatId: String): Flow<List<ChatMessageAndMemberRelation>>

  @Query("DELETE FROM chat_messages WHERE id = :id")
  suspend fun delete(id: String)

  @Query("DELETE FROM chat_messages WHERE id IN (:ids)")
  suspend fun delete(ids: List<String>)

  @Query("DELETE FROM chat_messages")
  suspend fun deleteAll()
}
