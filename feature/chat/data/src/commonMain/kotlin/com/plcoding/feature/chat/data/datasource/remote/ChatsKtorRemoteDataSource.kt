package com.plcoding.feature.chat.data.datasource.remote

import com.plcoding.core.data.tools.delete
import com.plcoding.core.data.tools.get
import com.plcoding.core.data.tools.post
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.asEmpty
import com.plcoding.core.domain.result.map
import com.plcoding.feature.chat.data.mapper.ChatMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberAmMapper
import com.plcoding.feature.chat.data.model.ChatAm
import com.plcoding.feature.chat.data.model.ChatCreateRequestAm
import com.plcoding.feature.chat.data.model.ChatMemberAm
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMember
import io.ktor.client.HttpClient
import io.ktor.client.request.delete

class ChatsKtorRemoteDataSource(
  private val httpClient: HttpClient,
) : ChatsRemoteDataSource {

  override suspend fun searchMember(query: String): Result<ChatMemberAm, DataError.Remote> {
    return httpClient.get<ChatMemberAm>(
      route = "/participants",
      params = mapOf("query" to query),
    )
  }

  override suspend fun getChat(chatId: String): Result<ChatAm, DataError.Remote> {
    return httpClient.get<ChatAm>(
      route = "/chat/$chatId",
    )
  }

  override suspend fun createChat(memberIds: List<String>): Result<ChatAm, DataError.Remote> {
    return httpClient.post<ChatCreateRequestAm, ChatAm>(
      route = "/chat",
      request = ChatCreateRequestAm(memberIds)
    )
  }

  override suspend fun getChats(): Result<List<ChatAm>, DataError.Remote> {
    return httpClient.get<List<ChatAm>>(
      route = "/chat"
    )
  }

  override suspend fun leaveChat(chatId: String): Empty<DataError.Remote> {
    return httpClient.delete<Unit>(
      route = "/chat/$chatId/leave"
    )
  }
}