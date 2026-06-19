package com.plcoding.feature.chat.data.datasource.remote

import com.plcoding.core.data.tools.get
import com.plcoding.core.data.tools.post
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.map
import com.plcoding.feature.chat.data.mapper.ChatMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberAmMapper
import com.plcoding.feature.chat.data.model.ChatAm
import com.plcoding.feature.chat.data.model.ChatCreateRequestAm
import com.plcoding.feature.chat.data.model.ChatMemberAm
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMember
import io.ktor.client.HttpClient

class ChatsKtorRemoteDataSource(
  private val httpClient: HttpClient,
  private val chatMapper: ChatMapper,
  private val chatMemberAmMapper: ChatMemberAmMapper,
) : ChatsRemoteDataSource {

  override suspend fun searchMember(query: String): Result<ChatMember, DataError.Remote> {
    return httpClient.get<ChatMemberAm>(
      route = "/participants",
      params = mapOf("query" to query),
    ).map {
      chatMemberAmMapper.map(it, Unit)
    }
  }

  override suspend fun getChat(chatId: String): Result<Chat, DataError.Remote> {
    return httpClient.get<ChatAm>(
      route = "/chat/$chatId",
    ).map {
      chatMapper.map(it, Unit)
    }
  }

  override suspend fun createChat(memberIds: List<String>): Result<Chat, DataError.Remote> {
    return httpClient.post<ChatCreateRequestAm, ChatAm>(
      route = "/chat",
      request = ChatCreateRequestAm(memberIds)
    ).map {
      chatMapper.map(it, Unit)
    }
  }

  override suspend fun getChats(): Result<List<Chat>, DataError.Remote> {
    return httpClient.get<List<ChatAm>>(
      route = "/chat"
    ).map {
      chatMapper.mapList(it, Unit)
    }
  }
}