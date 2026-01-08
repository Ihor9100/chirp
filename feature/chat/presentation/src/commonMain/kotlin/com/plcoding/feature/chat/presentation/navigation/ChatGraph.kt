package com.plcoding.feature.chat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plcoding.feature.chat.presentation.screen.chat.ChatScreen

fun NavGraphBuilder.chatGraph(
  navController: NavController,
) {
  navigation<ChatRoute.Graph>(
    startDestination = ChatRoute.Chat
  ) {
    composable<ChatRoute.Chat> {
      ChatScreen()
    }
  }
}