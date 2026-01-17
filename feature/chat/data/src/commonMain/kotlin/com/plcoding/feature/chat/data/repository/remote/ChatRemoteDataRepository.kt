package com.plcoding.feature.chat.data.repository.remote

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.domain.model.ChatMember
import com.plcoding.feature.chat.domain.repository.remote.ChatRemoteRepository
import io.ktor.client.HttpClient

class ChatRemoteDataRepository(
  private val httpClient: HttpClient,
) : ChatRemoteRepository {

  override suspend fun searchMember(query: String): Result<ChatMember, DataError.Remote> {
    TODO("Not yet implemented")
  }
}