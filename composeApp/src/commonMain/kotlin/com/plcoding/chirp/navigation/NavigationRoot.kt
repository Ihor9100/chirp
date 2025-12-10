package com.plcoding.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.plcoding.core.presentation.utils.navigateNewRoot
import com.plcoding.feature.auth.presentation.navigation.authGraph
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import com.plcoding.feature.chat.presentation.navigation.chatGraph

@Composable
fun NavigationRoot(
  navController: NavHostController,
  startDestination: Any,
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
  ) {
    authGraph(navController) {
      navController.navigateNewRoot(ChatRoute.Graph)
    }
    chatGraph(navController)
  }
}
