package com.plcoding.core.data.di

import com.plcoding.core.data.logger.KermitLogger
import com.plcoding.core.data.mapper.AuthInfoMapper
import com.plcoding.core.data.mapper.UserMapper
import com.plcoding.core.data.repository.local.PreferencesLocalDataRepository
import com.plcoding.core.data.repository.remote.AuthRemoteDataRepository
import com.plcoding.core.data.tools.HttpClientFactory
import com.plcoding.core.domain.logger.ChirpLogger
import com.plcoding.core.domain.repository.local.PreferencesLocalRepository
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataDiModule: Module

val coreDataDiModule = module {
  includes(platformCoreDataDiModule)

  single<Json> { Json { ignoreUnknownKeys = true } }
  single<ChirpLogger> { KermitLogger }
  single<HttpClient> { HttpClientFactory(get(), get(), get()).create(get()) }

  singleOf(::AuthRemoteDataRepository) bind AuthRemoteRepository::class
  singleOf(::PreferencesLocalDataRepository) bind PreferencesLocalRepository::class

  factoryOf(::UserMapper)
  factoryOf(::AuthInfoMapper)
}