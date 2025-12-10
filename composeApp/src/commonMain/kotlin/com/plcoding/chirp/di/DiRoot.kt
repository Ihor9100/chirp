package com.plcoding.chirp.di

import com.plcoding.chirp.screen.app.AppViewModel
import com.plcoding.core.data.di.coreDataDiModule
import com.plcoding.feature.auth.presentation.di.authPresentationDiModule
import com.plcoding.feature.chat.presentation.di.chatPresentationDiModule
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
  startKoin {
    config?.invoke(this)

    modules(
      rootModule,
      coreDataDiModule,
      authPresentationDiModule,
      chatPresentationDiModule,
    )
  }
}

val rootModule = module {
  viewModelOf(::AppViewModel)
}
