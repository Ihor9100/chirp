package com.plcoding.feature.auth.presentation.screen.forgot.password

sealed interface ForgotPasswordAction {
    data object OnSubmitClick: ForgotPasswordAction
}
