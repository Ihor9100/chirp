package com.plcoding.feature.chat.data.datasource.remote

import com.plcoding.core.data.tools.delete
import com.plcoding.core.data.tools.get
import com.plcoding.core.data.tools.post
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.data.datasource.utils.ChatsConstants
import com.plcoding.feature.chat.data.model.ChatCreateRequestDto
import com.plcoding.feature.chat.data.model.ChatDto
import com.plcoding.feature.chat.data.model.ChatMemberDto
import com.plcoding.feature.chat.data.model.ChatMembersDto
import com.plcoding.feature.chat.data.model.ChatMessageDto
import io.ktor.client.HttpClient

class ChatsKtorRemoteDataSource(
  private val httpClient: HttpClient,
) : ChatsRemoteDataSource {

  override suspend fun searchMember(query: String): Result<ChatMemberDto, DataError.Remote> {
    return httpClient.get<ChatMemberDto>(
      route = "/participants",
      params = mapOf("query" to query),
    )
  }

  override suspend fun getChat(chatId: String): Result<ChatDto, DataError.Remote> {
    return httpClient.get<ChatDto>(
      route = "/chat/$chatId",
    )
  }

  override suspend fun getChatMessages(
    chatId: String,
    before: String?,
  ): Result<List<ChatMessageDto>, DataError.Remote> {
    return httpClient.get(
      route = "/chat/$chatId/messages",
      params = buildMap {
        set("pageSize", ChatsConstants.PAGE_SIZE)
        before?.let { set("before", it) }
      }
    )
  }

  override suspend fun createChat(memberIds: List<String>): Result<ChatDto, DataError.Remote> {
    return httpClient.post<ChatCreateRequestDto, ChatDto>(
      route = "/chat",
      request = ChatCreateRequestDto(memberIds)
    )
  }

  override suspend fun getChats(): Result<List<ChatDto>, DataError.Remote> {
    return httpClient.get<List<ChatDto>>(
      route = "/chat"
    )
  }

  override suspend fun leaveChat(chatId: String): Empty<DataError.Remote> {
    return httpClient.delete<Unit>(
      route = "/chat/$chatId/leave"
    )
  }

  override suspend fun addChatMembers(
    chatId: String,
    memberIds: List<String>
  ): Result<ChatDto, DataError.Remote> {
    return httpClient.post<ChatMembersDto, ChatDto>(
      route = "/chat/$chatId/add",
      request = ChatMembersDto(memberIds),
    )
  }

  override suspend fun deleteChatMessage(messageId: String): Empty<DataError.Remote> {
    return httpClient.delete(
      route = "/messages/$messageId"
    )
  }
}