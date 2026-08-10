package com.plcoding.feature.chat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.plcoding.core.presentation.utils.NavResult
import com.plcoding.feature.chat.presentation.screen.chats.ChatsScreen
import com.plcoding.feature.chat.presentation.screen.chats.create.ChatCreateDialogScreen
import com.plcoding.feature.chat.presentation.screen.chats.manage.ChatManageDialogScreen
import com.plcoding.feature.chat.presentation.screen.user.profile.UserProfileDialogScreen

fun NavGraphBuilder.chatGraph(
  navController: NavController,
  navResult: NavResult,
) {
  navigation<ChatRoute.Graph>(
    startDestination = ChatRoute.Chats(),
  ) {
    composable<ChatRoute.Chats>(
      deepLinks = listOf(
        NavDeepLink("chirp://chat-details/{chatId}")
      ),
    ) {
      val route = it.toRoute<ChatRoute.Chats>()
      ChatsScreen(route.chatId,navController)
    }
    dialog<ChatRoute.ChatCreate> {
      ChatCreateDialogScreen(navController)
    }
    dialog<ChatRoute.ChatManage> {
      ChatManageDialogScreen(navController)
    }
    dialog<ChatRoute.UserProfile> {
      UserProfileDialogScreen(navController)
    }
  }
}