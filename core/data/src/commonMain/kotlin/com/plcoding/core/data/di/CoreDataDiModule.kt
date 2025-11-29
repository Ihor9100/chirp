package com.plcoding.core.data.di

import com.plcoding.core.data.logging.KermitLogger
import com.plcoding.core.data.networking.HttpClientFactory
import com.plcoding.core.data.networking.service.KtorAuthService
import com.plcoding.core.domain.logging.ChirpLogger
import com.plcoding.core.domain.networking.service.AuthService
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataDiModule: Module

val coreDataDiModule = module {
  includes(platformCoreDataDiModule)

  single<ChirpLogger> { KermitLogger }
  single<HttpClient> { HttpClientFactory(get()).create(get()) }

  singleOf(::KtorAuthService) bind AuthService::class
}