package com.plcoding.feature.chat.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.feature.chat.database.dao.ChatAndMemberDao
import com.plcoding.feature.chat.database.dao.ChatMembersDao
import com.plcoding.feature.chat.database.dao.ChatMessagesDao
import com.plcoding.feature.chat.database.dao.ChatsDao
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.view.ChatLastMessageView

@Database(
  entities = [
    ChatEntity::class,
    ChatMemberEntity::class,
    ChatMessageEntity::class,
    ChatAndMemberEntity::class,
  ],
  views = [
    ChatLastMessageView::class,
  ],
  version = 1,
)
@ConstructedBy(ChirpDatabaseConstructor::class)
abstract class ChirpDatabase : RoomDatabase() {

  companion object {
    const val NAME = "chirp.db"
  }

  abstract val chatsDao: ChatsDao
  abstract val chatMembersDao: ChatMembersDao
  abstract val chatMessagesDao: ChatMessagesDao
  abstract val chatAndMemberDao: ChatAndMemberDao
}
