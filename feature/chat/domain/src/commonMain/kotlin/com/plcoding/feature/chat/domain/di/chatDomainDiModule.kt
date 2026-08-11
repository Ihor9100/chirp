package com.plcoding.feature.chat.domain.di

import com.plcoding.feature.chat.domain.interactor.LogoutUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val chatDomainDiModule = module {
  singleOf(::LogoutUseCase)
}