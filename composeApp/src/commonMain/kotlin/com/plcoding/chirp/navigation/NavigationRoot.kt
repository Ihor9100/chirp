package com.plcoding.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.plcoding.feature.auth.presentation.navigation.AuthRoute
import com.plcoding.feature.auth.presentation.navigation.authGraph

@Composable
fun NavigationRoot() {
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = AuthRoute.Graph,
  ) {
    authGraph(navController)
  }
}
