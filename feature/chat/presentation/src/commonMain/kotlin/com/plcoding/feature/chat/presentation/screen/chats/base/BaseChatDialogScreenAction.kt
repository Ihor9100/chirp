package com.plcoding.feature.chat.presentation.screen.chats.base

sealed interface BaseChatDialogScreenAction {
  data object OnDismiss : BaseChatDialogScreenAction
  data object OnAddClick : BaseChatDialogScreenAction
  data object OnPositiveClick : BaseChatDialogScreenAction
}
