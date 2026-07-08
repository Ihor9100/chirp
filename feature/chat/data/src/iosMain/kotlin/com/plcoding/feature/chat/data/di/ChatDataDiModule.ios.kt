package com.plcoding.feature.chat.data.di

import com.plcoding.feature.chat.data.IOSAppLifecycleObserver
import com.plcoding.feature.chat.data.handler.IOSConnectionErrorHandler
import com.plcoding.feature.chat.data.observer.IOSAppConnectivityObserver
import com.plcoding.feature.chat.database.ChirpDatabaseBuilderFactory
import com.plcoding.feature.chat.domain.handler.ConnectionErrorHandler
import com.plcoding.feature.chat.domain.observer.AppConnectivityObserver
import com.plcoding.feature.chat.domain.observer.AppLifecycleObserver
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformChatDataDiModule = module {
  factoryOf(::ChirpDatabaseBuilderFactory)
  singleOf(::IOSAppLifecycleObserver) bind AppLifecycleObserver::class
  singleOf(::IOSAppConnectivityObserver) bind AppConnectivityObserver::class
  singleOf(::IOSConnectionErrorHandler) bind ConnectionErrorHandler::class
}