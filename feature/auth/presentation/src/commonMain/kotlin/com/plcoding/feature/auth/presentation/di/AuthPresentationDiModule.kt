package com.plcoding.feature.auth.presentation.di

import com.plcoding.feature.auth.presentation.screen.email.verification.EmailVerificationScreenViewModel
import com.plcoding.feature.auth.presentation.screen.forgot.password.ForgotPasswordScreenViewModel
import com.plcoding.feature.auth.presentation.screen.login.LoginScreenViewModel
import com.plcoding.feature.auth.presentation.screen.register.RegisterScreenViewModel
import com.plcoding.feature.auth.presentation.screen.register.success.RegisterSuccessScreenViewModel
import com.plcoding.feature.auth.presentation.screen.reset.password.ResetPasswordScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationDiModule = module {
  viewModelOf(::LoginScreenViewModel)
  viewModelOf(::ForgotPasswordScreenViewModel)
  viewModelOf(::ResetPasswordScreenViewModel)
  viewModelOf(::RegisterScreenViewModel)
  viewModelOf(::RegisterSuccessScreenViewModel)
  viewModelOf(::EmailVerificationScreenViewModel)
}
