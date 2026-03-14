package com.plcoding.feature.chat.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity

@Dao
interface ChatAndMemberDao {

  @Upsert
  suspend fun upsert(entity: ChatAndMemberEntity)

  @Upsert
  suspend fun upsert(entities: List<ChatAndMemberEntity>)
}
