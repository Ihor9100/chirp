package com.plcoding.feature.auth.presentation.screen.register

sealed interface RegisterEvent {

  data class Success(val email: String): RegisterEvent
}
