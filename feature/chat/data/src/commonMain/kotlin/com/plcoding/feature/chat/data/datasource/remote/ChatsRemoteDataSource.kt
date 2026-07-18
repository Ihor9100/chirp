package com.plcoding.feature.chat.data.datasource.remote

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.data.model.ChatDto
import com.plcoding.feature.chat.data.model.ChatMemberDto
import com.plcoding.feature.chat.data.model.ChatMessageDto

interface ChatsRemoteDataSource {

  suspend fun searchMember(query: String): Result<ChatMemberDto, DataError.Remote>

  suspend fun getChat(chatId: String): Result<ChatDto, DataError.Remote>

  suspend fun getChatMessages(
    chatId: String,
    before: String?
  ): Result<List<ChatMessageDto>, DataError.Remote>

  suspend fun createChat(memberIds: List<String>): Result<ChatDto, DataError.Remote>

  suspend fun getChats(): Result<List<ChatDto>, DataError.Remote>

  suspend fun leaveChat(chatId: String): Empty<DataError.Remote>

  suspend fun addChatMembers(
    chatId: String,
    memberIds: List<String>,
  ): Result<ChatDto, DataError.Remote>

  suspend fun deleteChatMessage(messageId: String): Empty<DataError.Remote>
}