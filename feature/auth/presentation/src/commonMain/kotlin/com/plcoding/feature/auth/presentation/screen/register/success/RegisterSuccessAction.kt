package com.plcoding.feature.auth.presentation.screen.register.success

sealed interface RegisterSuccessAction {
    data object PrimaryButtonClick: RegisterSuccessAction
    data object SecondaryButtonClick: RegisterSuccessAction
}
