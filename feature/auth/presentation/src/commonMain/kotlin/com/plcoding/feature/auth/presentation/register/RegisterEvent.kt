package com.plcoding.feature.auth.presentation.register

sealed interface RegisterEvent {

  data class Success(val email: String): RegisterEvent
}
