package com.plcoding.feature.auth.presentation.di

import com.plcoding.feature.auth.presentation.email.verification.EmailVerificationViewModel
import com.plcoding.feature.auth.presentation.register.RegisterViewModel
import com.plcoding.feature.auth.presentation.register.success.RegisterSuccessViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationDiModule = module {
  viewModelOf(::RegisterViewModel)
  viewModelOf(::RegisterSuccessViewModel)
  viewModelOf(::EmailVerificationViewModel)
}
