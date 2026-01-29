package com.plcoding.feature.chat.presentation.screen.chats.create

sealed interface ChatCreateDialogScreenAction {
  data object OnDismiss : ChatCreateDialogScreenAction
  data object OnAddClick : ChatCreateDialogScreenAction
  data object OnCreateDialogClick : ChatCreateDialogScreenAction
}