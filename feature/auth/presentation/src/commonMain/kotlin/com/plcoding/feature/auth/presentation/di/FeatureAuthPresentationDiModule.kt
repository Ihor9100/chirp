package com.plcoding.feature.auth.presentation.di

import com.plcoding.feature.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureAuthPresentationDiModule = module {
  viewModelOf(::RegisterViewModel)
}
