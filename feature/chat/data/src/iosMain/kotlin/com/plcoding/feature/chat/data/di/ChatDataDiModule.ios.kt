package com.plcoding.feature.chat.data.di

import com.plcoding.feature.chat.database.ChirpDatabaseBuilderFactory
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual val platformChatDataDiModule = module {
  factoryOf(::ChirpDatabaseBuilderFactory)
}