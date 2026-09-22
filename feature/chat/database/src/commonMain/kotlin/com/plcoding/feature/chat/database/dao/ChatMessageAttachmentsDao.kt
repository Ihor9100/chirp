package com.plcoding.feature.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.plcoding.feature.chat.database.entity.ChatMessageAttachmentEntity

@Dao
interface ChatMessageAttachmentsDao {

  @Upsert
  suspend fun upsert(entities: List<ChatMessageAttachmentEntity>)

  @Query("DELETE FROM chat_message_attachments WHERE messageId IN (:messageIds)")
  suspend fun deleteForMessages(messageIds: List<String>)

  @Query(
    """
      DELETE FROM chat_message_attachments
      WHERE messageId IN (
        SELECT id FROM chat_messages WHERE chatId = :chatId
      )
    """
  )
  suspend fun deleteForChat(chatId: String)

  @Query("SELECT * FROM chat_message_attachments WHERE messageId = :messageId")
  suspend fun getForMessage(messageId: String): List<ChatMessageAttachmentEntity>

  @Query("DELETE FROM chat_message_attachments")
  suspend fun deleteAll()
}
