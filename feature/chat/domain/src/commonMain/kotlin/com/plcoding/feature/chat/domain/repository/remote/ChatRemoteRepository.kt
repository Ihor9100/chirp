package com.plcoding.feature.chat.domain.repository.remote

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMember

interface ChatRemoteRepository {

  suspend fun searchMember(
    query: String,
  ): Result<ChatMember, DataError.Remote>

  suspend fun createChat(
    memberIds: List<String>,
  ): Result<Chat, DataError.Remote>
}
