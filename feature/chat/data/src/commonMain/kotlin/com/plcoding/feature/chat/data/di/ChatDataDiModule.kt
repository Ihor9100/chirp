package com.plcoding.feature.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.plcoding.feature.chat.data.mapper.ChatMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberAmMapper
import com.plcoding.feature.chat.data.mapper.ChatMessageMapper
import com.plcoding.feature.chat.data.repository.ChatDataRepository
import com.plcoding.feature.chat.database.ChirpDatabase
import com.plcoding.feature.chat.database.ChirpDatabaseBuilderFactory
import com.plcoding.feature.chat.domain.repository.ChatRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataDiModule: Module

val chatDataDiModule = module {
  includes(platformChatDataDiModule)

  singleOf(::ChatDataRepository) bind ChatRepository::class
  single<ChirpDatabase> {
    get<ChirpDatabaseBuilderFactory>()
      .create()
      .setDriver(BundledSQLiteDriver())
      .build()
  }

  factoryOf(::ChatMapper)
  factoryOf(::ChatMemberAmMapper)
  factoryOf(::ChatMessageMapper)
}
