package com.plcoding.feature.chat.data.di

import com.plcoding.feature.chat.data.AppLifecycleDataObserver
import com.plcoding.feature.chat.database.ChirpDatabaseBuilderFactory
import com.plcoding.feature.chat.domain.observer.AppLifecycleObserver
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformChatDataDiModule = module {
  factoryOf(::ChirpDatabaseBuilderFactory)
  singleOf(::AppLifecycleDataObserver) bind AppLifecycleObserver::class
}