package com.plcoding.feature.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.plcoding.feature.auth.presentation.screen.email.verification.EmailVerificationScreen
import com.plcoding.feature.auth.presentation.screen.login.LoginScreenScreen
import com.plcoding.feature.auth.presentation.screen.register.RegisterScreen
import com.plcoding.feature.auth.presentation.screen.register.success.RegisterSuccessScreen

fun NavGraphBuilder.authGraph(
  navController: NavController,
) {
  navigation<AuthRoute.Graph>(
    startDestination = AuthRoute.Login
  ) {
    composable<AuthRoute.Login> {
      LoginScreenScreen {
        navController.navigate(AuthRoute.Register)
      }
    }
    composable<AuthRoute.Register> {
      RegisterScreen(
        openRegisterSuccess = { navController.navigate(AuthRoute.RegisterSuccess(it)) },
        openLogin = { navController.navigate(AuthRoute.Login) }
      )
    }
    composable<AuthRoute.RegisterSuccess> {
      RegisterSuccessScreen()
    }
    composable<AuthRoute.EmailVerification>(
      deepLinks = listOf(
        navDeepLink { uriPattern = "https://chirp.pl-coding.com/api/auth/verify?token={token}" },
        navDeepLink { uriPattern = "chirp://chirp.pl-coding.com/api/auth/verify?token={token}" },
      )
    ) {
      EmailVerificationScreen()
    }
  }
}