package com.plcoding.feature.auth.presentation.screen.register

sealed interface RegisterScreenEvent {

  data class Success(val email: String): RegisterScreenEvent
}
