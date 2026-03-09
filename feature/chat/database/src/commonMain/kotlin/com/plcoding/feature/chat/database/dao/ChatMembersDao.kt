package com.plcoding.feature.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.relation.ChatAndMembersAndMessagesRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMembersDao {

  @Upsert
  suspend fun upsert(entity: ChatMemberEntity)

  @Upsert
  suspend fun upsert(entities: List<ChatMemberEntity>)

  @Query("SELECT * FROM chat_members")
  suspend fun getAll(): List<ChatMemberEntity>
}
