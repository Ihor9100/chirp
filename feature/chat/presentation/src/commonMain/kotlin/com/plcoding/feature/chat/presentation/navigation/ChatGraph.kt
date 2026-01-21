package com.plcoding.feature.chat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.plcoding.core.presentation.utils.NavResult
import com.plcoding.feature.chat.presentation.screen.chat.ChatScreen
import com.plcoding.feature.chat.presentation.screen.chat.create.ChatCreateScreen

fun NavGraphBuilder.chatGraph(
  navController: NavController,
  navResult: NavResult,
) {
  navigation<ChatRoute.Graph>(
    startDestination = ChatRoute.Chat
  ) {
    composable<ChatRoute.Chat> {
      ChatScreen(navController, navResult)
    }
    dialog<ChatRoute.ChatCreate> {
      ChatCreateScreen(navController, navResult)
    }
  }
}