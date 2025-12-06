package com.plcoding.core.data.di

import com.plcoding.core.data.logger.KermitLogger
import com.plcoding.core.data.network.HttpClientFactory
import com.plcoding.core.data.network.mapper.AuthInfoMapper
import com.plcoding.core.data.network.mapper.UserMapper
import com.plcoding.core.data.network.service.KtorAuthService
import com.plcoding.core.data.storage.DataStoreSessionStorage
import com.plcoding.core.domain.logger.ChirpLogger
import com.plcoding.core.domain.network.service.AuthService
import com.plcoding.core.domain.storage.SessionStorage
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataDiModule: Module

val coreDataDiModule = module {
  includes(platformCoreDataDiModule)

  single<ChirpLogger> { KermitLogger }
  single<HttpClient> { HttpClientFactory(get()).create(get()) }

  singleOf(::KtorAuthService) bind AuthService::class
  singleOf(::DataStoreSessionStorage) bind SessionStorage::class

  factoryOf(::UserMapper)
  factoryOf(::AuthInfoMapper)
}