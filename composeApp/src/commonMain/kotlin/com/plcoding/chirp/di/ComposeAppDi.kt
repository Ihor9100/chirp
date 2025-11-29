package com.plcoding.chirp.di

import com.plcoding.core.data.di.coreDataDiModule
import com.plcoding.feature.auth.presentation.di.featureAuthPresentationDiModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
  startKoin {
    config?.invoke(this)

    modules(
      coreDataDiModule,
      featureAuthPresentationDiModule,
    )
  }
}
