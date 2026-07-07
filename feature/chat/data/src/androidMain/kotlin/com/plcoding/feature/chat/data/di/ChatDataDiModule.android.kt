package com.plcoding.feature.chat.data.di

import com.plcoding.feature.chat.data.observer.AppConnectivityDataObserver
import com.plcoding.feature.chat.data.observer.AppLifecycleDataObserver
import com.plcoding.feature.chat.database.ChirpDatabaseBuilderFactory
import com.plcoding.feature.chat.domain.observer.AppConnectivityObserver
import com.plcoding.feature.chat.domain.observer.AppLifecycleObserver
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformChatDataDiModule = module {
  factory { ChirpDatabaseBuilderFactory(androidContext()) }
  singleOf(::AppLifecycleDataObserver) bind AppLifecycleObserver::class
  singleOf(::AppConnectivityDataObserver) bind AppConnectivityObserver::class
}