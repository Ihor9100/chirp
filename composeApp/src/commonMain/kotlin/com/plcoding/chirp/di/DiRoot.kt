package com.plcoding.chirp.di

import com.plcoding.core.data.di.coreDataDiModule
import com.plcoding.feature.auth.presentation.di.authPresentationDiModule
import com.plcoding.feature.chat.data.di.chatDataDiModule
import com.plcoding.feature.chat.presentation.di.chatPresentationDiModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
  startKoin {
    config?.invoke(this)

    modules(
      appModule,
      coreDataDiModule,
      authPresentationDiModule,
      chatDataDiModule,
      chatPresentationDiModule,
    )
  }
}
