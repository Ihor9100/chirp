package com.plcoding.feature.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.plcoding.core.presentation.utils.navigateFresh
import com.plcoding.core.presentation.utils.navigateWithPopUpTo
import com.plcoding.feature.auth.presentation.screen.email.verification.EmailVerificationScreen
import com.plcoding.feature.auth.presentation.screen.forgot.password.ForgotPasswordScreen
import com.plcoding.feature.auth.presentation.screen.login.LoginScreen
import com.plcoding.feature.auth.presentation.screen.register.RegisterScreen
import com.plcoding.feature.auth.presentation.screen.register.success.RegisterSuccessScreen

fun NavGraphBuilder.authGraph(
  navController: NavController,
  openChat: () -> Unit,
) {
  navigation<AuthRoute.Graph>(
    startDestination = AuthRoute.Login
  ) {
    composable<AuthRoute.Login> {
      LoginScreen(
        openChat = openChat,
        openForgotPassword = { navController.navigate(AuthRoute.ForgotPassword) },
        openRegisterScreen = { navController.navigate(AuthRoute.Register) }
      )
    }
    composable<AuthRoute.ForgotPassword> {
      ForgotPasswordScreen()
    }
    composable<AuthRoute.Register> {
      RegisterScreen(
        openRegisterSuccess = {
          navController.navigateWithPopUpTo(
            destination = AuthRoute.RegisterSuccess(it),
            popUpTo = AuthRoute.Register,
          )
        },
        openLogin = {
          navController.navigateFresh(AuthRoute.Login)
        }
      )
    }
    composable<AuthRoute.RegisterSuccess> {
      RegisterSuccessScreen {
        navController.navigateFresh(AuthRoute.Login)
      }
    }
    composable<AuthRoute.EmailVerification>(
      deepLinks = listOf(
        navDeepLink {
          uriPattern =
            "https://chirp.pl-coding.com/api/auth/verify?token={token}"
        },
        navDeepLink {
          uriPattern =
            "chirp://chirp.pl-coding.com/api/auth/verify?token={token}"
        },
      )
    ) {
      EmailVerificationScreen {
        navController.navigateFresh(AuthRoute.Login)
      }
    }
  }
}