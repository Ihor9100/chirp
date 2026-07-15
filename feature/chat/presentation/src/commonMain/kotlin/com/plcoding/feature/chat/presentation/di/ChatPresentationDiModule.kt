package com.plcoding.feature.chat.presentation.di

import com.plcoding.feature.chat.presentation.screen.chats.ChatsScreenViewModel
import com.plcoding.feature.chat.presentation.screen.chats.create.ChatCreateDialogScreenViewModel
import com.plcoding.feature.chat.presentation.screen.chats.details.ChatDetailsScreenViewModel
import com.plcoding.feature.chat.presentation.screen.chats.list.ChatsListScreenViewModel
import com.plcoding.feature.chat.presentation.screen.chats.manage.ChatManageDialogScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatPresentationDiModule = module {
  viewModelOf(::ChatsScreenViewModel)
  viewModelOf(::ChatsListScreenViewModel)
  viewModelOf(::ChatDetailsScreenViewModel)
  viewModelOf(::ChatCreateDialogScreenViewModel)
  viewModelOf(::ChatManageDialogScreenViewModel)
}