package com.plcoding.core.data.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformCoreDataDiModule = module {
  single<HttpClientEngine> { Darwin.create() }
}