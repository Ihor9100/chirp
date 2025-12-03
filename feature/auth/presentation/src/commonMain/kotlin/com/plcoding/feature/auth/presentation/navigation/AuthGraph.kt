package com.plcoding.feature.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.plcoding.feature.auth.presentation.email.verification.EmailVerificationScreen
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
    composable<AuthRoute.EmailVerification>(
      deepLinks = listOf(
        navDeepLink<AuthRoute.EmailVerification>("https://chirp.pl-coding.com/api/auth/verify"),
        navDeepLink<AuthRoute.EmailVerification>("chirp://chirp.pl-coding.com/api/auth/verify"),
      )
    ) {
      EmailVerificationScreen()
    }
  }
}