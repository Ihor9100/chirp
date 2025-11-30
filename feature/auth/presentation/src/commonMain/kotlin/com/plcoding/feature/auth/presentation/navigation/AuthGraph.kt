package com.plcoding.feature.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plcoding.feature.auth.presentation.register.RegisterScreen
import com.plcoding.feature.auth.presentation.register.success.RegisterSuccessScreen

fun NavGraphBuilder.authGraph(
  navController: NavController,
) {
  navigation<AuthRoute.Graph>(
    startDestination = AuthRoute.Register
  ) {
    composable<AuthRoute.Register> {
      RegisterScreen {
        navController.navigate(AuthRoute.RegisterSuccess(it))
      }
    }
    composable<AuthRoute.RegisterSuccess> {
      RegisterSuccessScreen()
    }
  }
}