package com.plcoding.feature.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.plcoding.feature.chat.data.datasource.local.ChatsLocalDataSource
import com.plcoding.feature.chat.data.datasource.local.ChatsRoomLocalDataSource
import com.plcoding.feature.chat.data.datasource.remote.ChatsKtorRemoteDataSource
import com.plcoding.feature.chat.data.datasource.remote.ChatsRemoteDataSource
import com.plcoding.feature.chat.data.network.ConnectionRetryHandler
import com.plcoding.feature.chat.data.mapper.ChatAndMembersRelationMapper
import com.plcoding.feature.chat.data.mapper.ChatDetailsMapper
import com.plcoding.feature.chat.data.mapper.ChatEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberAmMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMessageAndMemberMapper
import com.plcoding.feature.chat.data.mapper.ChatMessageEntityMapper
import com.plcoding.feature.chat.data.mapper.ChatMessageMapper
import com.plcoding.feature.chat.data.mapper.NewMessageAmMapper
import com.plcoding.feature.chat.data.mapper.ChatsAndMembersEntityMapper
import com.plcoding.feature.chat.data.repository.ChatDataRepository
import com.plcoding.feature.chat.database.ChirpDatabase
import com.plcoding.feature.chat.database.ChirpDatabaseBuilderFactory
import com.plcoding.feature.chat.database.dao.ChatAndMemberDao
import com.plcoding.feature.chat.database.dao.ChatMembersDao
import com.plcoding.feature.chat.database.dao.ChatMessagesDao
import com.plcoding.feature.chat.database.dao.ChatsDao
import com.plcoding.feature.chat.domain.repository.ChatRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataDiModule: Module

val chatDataDiModule = module {
  includes(platformChatDataDiModule)

  single<ChirpDatabase> {
    get<ChirpDatabaseBuilderFactory>()
      .create()
      .setDriver(BundledSQLiteDriver())
      .build()
  }

  singleOf(::ConnectionRetryHandler)

  singleOf(::ChatDataRepository) bind ChatRepository::class
  singleOf(::ChatsKtorRemoteDataSource) bind ChatsRemoteDataSource::class
  singleOf(::ChatsRoomLocalDataSource) bind ChatsLocalDataSource::class

  single<ChatsDao> { get<ChirpDatabase>().chatsDao }
  single<ChatMembersDao> { get<ChirpDatabase>().chatMembersDao }
  single<ChatMessagesDao> { get<ChirpDatabase>().chatMessagesDao }
  single<ChatAndMemberDao> { get<ChirpDatabase>().chatAndMemberDao }

  factoryOf(::ChatEntityMapper)
  factoryOf(::ChatMemberEntityMapper)
  factoryOf(::ChatMessageEntityMapper)
  factoryOf(::ChatAndMembersRelationMapper)
  factoryOf(::ChatsAndMembersEntityMapper)
  factoryOf(::ChatMapper)
  factoryOf(::ChatMemberAmMapper)
  factoryOf(::ChatMessageMapper)
  factoryOf(::NewMessageAmMapper)
  factoryOf(::ChatMessageAndMemberMapper)
  factoryOf(::ChatDetailsMapper)
}
