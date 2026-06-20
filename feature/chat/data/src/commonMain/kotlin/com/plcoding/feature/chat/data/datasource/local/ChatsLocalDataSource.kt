package com.plcoding.feature.chat.data.datasource.local

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.relation.ChatAndMembersAndMessagesRelation
import com.plcoding.feature.chat.database.relation.ChatAndMembersRelation
import kotlinx.coroutines.flow.Flow

interface ChatsLocalDataSource {

  suspend fun observeChatAndMembers(): Flow<List<ChatAndMembersRelation>>

  suspend fun observeChatAndMembersAndMessages(chatId: String): Flow<ChatAndMembersAndMessagesRelation>

  suspend fun saveChatsDetails(
    chats: List<ChatEntity>,
    chatMembers: List<ChatMemberEntity>,
    chatMessages: List<ChatMessageEntity>,
    chatsAndMembers: List<ChatAndMemberEntity>,
  ): Empty<DataError.Local>
}