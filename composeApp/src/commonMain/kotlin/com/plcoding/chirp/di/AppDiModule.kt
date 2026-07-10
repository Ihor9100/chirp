package com.plcoding.chirp.di

import com.plcoding.chirp.screen.app.AppScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
  viewModelOf(::AppScreenViewModel)
  single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
}
