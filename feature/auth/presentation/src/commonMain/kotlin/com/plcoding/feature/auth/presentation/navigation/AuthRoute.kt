package com.plcoding.feature.auth.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthRoute {

  data object Register : AuthRoute

  data class RegisterSuccess(val email: String) : AuthRoute
}
