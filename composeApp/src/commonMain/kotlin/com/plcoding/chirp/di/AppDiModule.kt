package com.plcoding.chirp.di

import com.plcoding.chirp.screen.app.AppScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
  viewModelOf(::AppScreenViewModel)
}
