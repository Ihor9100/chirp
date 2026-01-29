package com.plcoding.feature.chat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.plcoding.core.presentation.utils.NavResult
import com.plcoding.feature.chat.presentation.screen.chats.ChatsScreen
import com.plcoding.feature.chat.presentation.screen.chats.create.ChatCreateDialogScreen

fun NavGraphBuilder.chatGraph(
  navController: NavController,
  navResult: NavResult,
) {
  navigation<ChatRoute.Graph>(
    startDestination = ChatRoute.Chats
  ) {
    composable<ChatRoute.Chats> {
      ChatsScreen(navController, navResult)
    }
    dialog<ChatRoute.ChatCreate> {
      ChatCreateDialogScreen(navController, navResult)
    }
  }
}