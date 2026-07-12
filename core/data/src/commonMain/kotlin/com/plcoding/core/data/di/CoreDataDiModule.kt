package com.plcoding.core.data.di

import com.plcoding.core.data.logger.KermitLogger
import com.plcoding.core.data.repository.PreferencesDataRepository
import com.plcoding.core.data.repository.AuthDataRepository
import com.plcoding.core.data.tools.HttpClientFactory
import com.plcoding.core.domain.logger.Logger
import com.plcoding.core.domain.repository.PreferencesRepository
import com.plcoding.core.domain.repository.AuthRepository
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataDiModule: Module

val coreDataDiModule = module {
  includes(platformCoreDataDiModule)

  single<Json> { Json { ignoreUnknownKeys = true } }
  single<Logger> { KermitLogger }
  single<HttpClient> { get<HttpClientFactory>().create() }

  singleOf(::HttpClientFactory)
  singleOf(::AuthDataRepository) bind AuthRepository::class
  singleOf(::PreferencesDataRepository) bind PreferencesRepository::class
}