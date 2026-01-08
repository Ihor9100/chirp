package com.plcoding.feature.chat.presentation.di

import com.plcoding.feature.chat.presentation.screen.chat.ChatScreenViewModel
import com.plcoding.feature.chat.presentation.screen.chat.create.ChatCreateScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatPresentationDiModule = module {
  viewModelOf(::ChatScreenViewModel)
  viewModelOf(::ChatCreateScreenViewModel)
}