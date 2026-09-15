package com.plcoding.feature.auth.presentation.screen.register.success

sealed interface RegisterSuccessScreenAction {
    data object OnLoginClick: RegisterSuccessScreenAction
    data object OnResendClick: RegisterSuccessScreenAction
}
