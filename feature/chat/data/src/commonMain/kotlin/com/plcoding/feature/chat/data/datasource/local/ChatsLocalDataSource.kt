package com.plcoding.feature.chat.data.datasource.local

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.feature.chat.domain.model.Chat
import com.plcoding.feature.chat.domain.model.ChatMember
import kotlinx.coroutines.flow.Flow

interface ChatsLocalDataSource {

  suspend fun observeChats(): Flow<List<Chat>>

  suspend fun saveChats(chats: List<Chat>): Empty<DataError.Local>
}