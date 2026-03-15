package com.plcoding.feature.chat.data.di

import com.plcoding.feature.chat.database.ChirpDatabaseBuilderFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformChatDataDiModule = module {
  factory { ChirpDatabaseBuilderFactory(androidContext()) }
}