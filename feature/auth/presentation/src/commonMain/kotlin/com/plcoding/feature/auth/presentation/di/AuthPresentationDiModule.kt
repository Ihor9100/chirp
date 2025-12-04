package com.plcoding.feature.auth.presentation.di

import com.plcoding.feature.auth.presentation.screen.email.verification.EmailVerificationViewModel
import com.plcoding.feature.auth.presentation.screen.login.LoginViewModel
import com.plcoding.feature.auth.presentation.screen.register.RegisterViewModel
import com.plcoding.feature.auth.presentation.screen.register.success.RegisterSuccessViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationDiModule = module {
  viewModelOf(::LoginViewModel)
  viewModelOf(::RegisterViewModel)
  viewModelOf(::RegisterSuccessViewModel)
  viewModelOf(::EmailVerificationViewModel)
}
