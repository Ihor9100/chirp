package com.plcoding.feature.chat.presentation.screen.chats.create

sealed interface ChatCreateScreenAction {
  data object OnDismiss : ChatCreateScreenAction
  data object OnAddClick : ChatCreateScreenAction
  data object OnCreateClick : ChatCreateScreenAction
}