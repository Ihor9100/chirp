package com.plcoding.feature.chat.data.datasource.local

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.database.entity.ChatAndMemberEntity
import com.plcoding.feature.chat.database.entity.ChatEntity
import com.plcoding.feature.chat.database.entity.ChatMemberEntity
import com.plcoding.feature.chat.database.entity.ChatMessageEntity
import com.plcoding.feature.chat.database.relation.ChatAndMembersAndMessagesRelation
import com.plcoding.feature.chat.database.relation.ChatAndMembersRelation
import com.plcoding.feature.chat.database.relation.ChatMessageAndMemberRelation
import com.plcoding.feature.chat.domain.model.ChatMessageDeliveryStatus
import kotlinx.coroutines.flow.Flow

interface ChatsLocalDataSource {
  fun observeChatMembers(chatId: String): Flow<List<ChatMemberEntity>>
  fun observeChatMessages(chatId: String): Flow<List<ChatMessageAndMemberRelation>>
  fun observeChatAndMembers(): Flow<List<ChatAndMembersRelation>>
  fun observeChatAndMembersAndMessages(chatId: String): Flow<ChatAndMembersAndMessagesRelation?>

  suspend fun updateChatMember(id: String, avatarUrl: String?): Empty<DataError.Local>
  suspend fun upsertChatMessage(entity: ChatMessageEntity): Empty<DataError.Local>
  suspend fun updateChatMessage(id: String, deliveryStatus: ChatMessageDeliveryStatus)

  suspend fun deleteChatMessage(id: String)
  suspend fun replaceChatMessages(entities: List<ChatMessageEntity>): Empty<DataError.Local>
  suspend fun hasChat(id: String): Result<Boolean, DataError.Local>
  suspend fun getChatMessage(id: String): ChatMessageEntity?

  suspend fun upsertChatDetails(
    chat: ChatEntity,
    chatMembers: List<ChatMemberEntity>,
    chatMessages: List<ChatMessageEntity>,
    chatsAndMembers: List<ChatAndMemberEntity>,
  ): Empty<DataError.Local>

  suspend fun replaceChatsDetails(
    chats: List<ChatEntity>,
    chatMembers: List<ChatMemberEntity>,
    chatMessages: List<ChatMessageEntity>,
    chatsAndMembers: List<ChatAndMemberEntity>,
  ): Empty<DataError.Local>

  suspend fun removeChatDetails(chatId: String): Empty<DataError.Local>
}