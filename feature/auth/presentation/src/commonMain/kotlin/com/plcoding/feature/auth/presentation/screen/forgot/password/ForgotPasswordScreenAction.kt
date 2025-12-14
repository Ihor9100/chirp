package com.plcoding.feature.auth.presentation.screen.forgot.password

sealed interface ForgotPasswordScreenAction {
    data object OnSubmitClick: ForgotPasswordScreenAction
}
