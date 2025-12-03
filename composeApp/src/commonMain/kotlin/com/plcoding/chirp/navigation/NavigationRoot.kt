package com.plcoding.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.plcoding.feature.auth.presentation.navigation.AuthRoute
import com.plcoding.feature.auth.presentation.navigation.authGraph

@Composable
fun NavigationRoot(navController: NavHostController) {
  NavHost(
    navController = navController,
    startDestination = AuthRoute.Graph,
  ) {
    authGraph(navController)
  }
}
