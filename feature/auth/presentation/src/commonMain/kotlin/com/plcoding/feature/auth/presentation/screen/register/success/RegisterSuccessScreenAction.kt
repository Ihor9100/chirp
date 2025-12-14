package com.plcoding.feature.auth.presentation.screen.register.success

sealed interface RegisterSuccessScreenAction {
    data object PrimaryButtonClick: RegisterSuccessScreenAction
    data object SecondaryButtonClick: RegisterSuccessScreenAction
}
