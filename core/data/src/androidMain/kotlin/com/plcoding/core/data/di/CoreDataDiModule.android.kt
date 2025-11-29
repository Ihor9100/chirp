package com.plcoding.core.data.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val platformCoreDataDiModule = module {
  single<HttpClientEngine> { OkHttp.create() }
}