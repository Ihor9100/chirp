package com.plcoding.feature.chat.data.di

import com.plcoding.feature.chat.data.handler.iOSConnectionErrorHandler
import com.plcoding.feature.chat.data.iOSAppLifecycleObserver
import com.plcoding.feature.chat.data.observer.iOSAppConnectivityObserver
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
  singleOf(::iOSAppLifecycleObserver) bind AppLifecycleObserver::class
  singleOf(::iOSAppConnectivityObserver) bind AppConnectivityObserver::class
  singleOf(::iOSConnectionErrorHandler) bind ConnectionErrorHandler::class
}