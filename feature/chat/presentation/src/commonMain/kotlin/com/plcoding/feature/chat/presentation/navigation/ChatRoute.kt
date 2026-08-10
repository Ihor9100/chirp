package com.plcoding.feature.chat.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ChatRoute {

  @Serializable
  data object Graph : ChatRoute

  @Serializable
  data class Chats(val chatId: String? = null) : ChatRoute

  @Serializable
  data object ChatCreate : ChatRoute

  @Serializable
  data class ChatManage(val chatId: String) : ChatRoute

  @Serializable
  data object UserProfile : ChatRoute
}
