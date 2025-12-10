package com.plcoding.feature.chat.presentation.di

import com.plcoding.feature.chat.presentation.screen.ChatViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatPresentationDiModule = module {
  viewModelOf(::ChatViewModel)
}