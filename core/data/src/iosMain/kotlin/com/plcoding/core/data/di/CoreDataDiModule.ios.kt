package com.plcoding.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.plcoding.core.data.storage.createDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformCoreDataDiModule = module {
  single<HttpClientEngine> { Darwin.create() }
  single<DataStore<Preferences>> { createDataStore() }
}