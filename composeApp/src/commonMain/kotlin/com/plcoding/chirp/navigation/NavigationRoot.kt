package com.plcoding.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.plcoding.core.presentation.utils.LocalNavResult
import com.plcoding.core.presentation.utils.NavResult
import com.plcoding.core.presentation.utils.navigateNewRoot
import com.plcoding.core.presentation.utils.rememberNavResult
import com.plcoding.feature.auth.presentation.navigation.authGraph
import com.plcoding.feature.chat.presentation.navigation.ChatRoute
import com.plcoding.feature.chat.presentation.navigation.chatGraph

@Composable
fun NavigationRoot(
  navController: NavHostController,
  startDestination: Any,
) {
  val navResult = rememberNavResult()

  CompositionLocalProvider(LocalNavResult provides navResult) {
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
}
