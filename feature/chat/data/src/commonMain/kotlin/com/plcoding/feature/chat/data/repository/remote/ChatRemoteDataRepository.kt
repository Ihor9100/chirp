package com.plcoding.feature.chat.data.repository.remote

import com.plcoding.core.data.tools.get
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.map
import com.plcoding.feature.chat.data.mapper.ChatMemberMapper
import com.plcoding.feature.chat.data.model.ChatMemberAm
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.repository.remote.ChatRemoteRepository
import io.ktor.client.HttpClient

class ChatRemoteDataRepository(
  private val httpClient: HttpClient,
  private val chatMemberMapper: ChatMemberMapper,
) : ChatRemoteRepository {

  override suspend fun searchMember(query: String): Result<ChatMember, DataError.Remote> {
    return httpClient.get<ChatMemberAm>(
      route = "/participants",
      params = mapOf("query" to query),
    ).map {
      chatMemberMapper.map(it, Unit)
    }
  }
}