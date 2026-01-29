package com.plcoding.feature.chat.presentation.di

import com.plcoding.feature.chat.presentation.mapper.ChatMemberPmMapper
import com.plcoding.feature.chat.presentation.screen.chats.ChatsScreenViewModel
import com.plcoding.feature.chat.presentation.screen.chats.create.ChatCreateScreenViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatPresentationDiModule = module {
  viewModelOf(::ChatsScreenViewModel)
  viewModelOf(::ChatCreateScreenViewModel)

  factoryOf(::ChatMemberPmMapper)
}