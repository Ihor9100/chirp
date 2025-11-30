package com.plcoding.feature.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

fun NavGraphBuilder.authGraph(
  navController: NavController,
) {
  navigation<AuthRoute.Graph>(
    startDestination = AuthRoute.Register
  ) {
    composable<AuthRoute.Register> {

    }
  }
}