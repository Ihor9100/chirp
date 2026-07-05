package com.plcoding.feature.chat.data.datasource.remote

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.data.model.ChatAm
import com.plcoding.feature.chat.data.model.ChatMemberAm

interface ChatsRemoteDataSource {

  suspend fun searchMember(query: String): Result<ChatMemberAm, DataError.Remote>

  suspend fun getChat(chatId: String): Result<ChatAm, DataError.Remote>

  suspend fun createChat(memberIds: List<String>): Result<ChatAm, DataError.Remote>

  suspend fun getChats(): Result<List<ChatAm>, DataError.Remote>

  suspend fun leaveChat(chatId: String): Empty<DataError.Remote>

  suspend fun addChatMembers(
    chatId: String,
    memberIds: List<String>,
  ): Result<ChatAm, DataError.Remote>
}