package com.plcoding.feature.chat.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ChatRoute {

  @Serializable
  data object Graph : ChatRoute

  @Serializable
  data object Chats : ChatRoute

  @Serializable
  data object ChatCreate : ChatRoute
}
