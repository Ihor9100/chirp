package com.plcoding.feature.chat.data.di

import com.plcoding.feature.chat.data.handler.AndroidConnectionErrorHandler
import com.plcoding.feature.chat.data.observer.AndroidAppConnectivityObserver
import com.plcoding.feature.chat.data.observer.AndroidAppLifecycleObserver
import com.plcoding.feature.chat.database.ChirpDatabaseBuilderFactory
import com.plcoding.feature.chat.domain.handler.ConnectionErrorHandler
import com.plcoding.feature.chat.domain.observer.AppConnectivityObserver
import com.plcoding.feature.chat.domain.observer.AppLifecycleObserver
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformChatDataDiModule = module {
  factory { ChirpDatabaseBuilderFactory(androidContext()) }
  singleOf(::AndroidAppLifecycleObserver) bind AppLifecycleObserver::class
  singleOf(::AndroidAppConnectivityObserver) bind AppConnectivityObserver::class
  singleOf(::AndroidConnectionErrorHandler) bind ConnectionErrorHandler::class
}