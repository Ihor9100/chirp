package com.plcoding.feature.auth.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AuthRoute {

  @Serializable
  data object Graph : AuthRoute

  @Serializable
  data object Login : AuthRoute

  @Serializable
  data object ForgotPassword : AuthRoute

  @Serializable
  data object Register : AuthRoute

  @Serializable
  data class RegisterSuccess(val email: String) : AuthRoute

  @Serializable
  data class EmailVerification(val token: String) : AuthRoute
}
