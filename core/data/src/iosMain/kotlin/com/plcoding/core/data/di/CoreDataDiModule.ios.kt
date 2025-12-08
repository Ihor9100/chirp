package com.plcoding.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.plcoding.core.data.repository.CryptographyDataRepository
import com.plcoding.core.data.tools.createDataStore
import com.plcoding.core.domain.repository.CryptographyRepository
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformCoreDataDiModule = module {
  single<HttpClientEngine> { Darwin.create() }
  single<DataStore<Preferences>> { createDataStore() }
  single<CryptographyRepository> { CryptographyDataRepository() }
}