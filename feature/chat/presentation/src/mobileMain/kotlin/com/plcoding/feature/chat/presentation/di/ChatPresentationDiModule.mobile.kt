package com.plcoding.feature.chat.presentation.di

import com.plcoding.feature.chat.presentation.permissions.MobilePermissionsManagerFactory
import com.plcoding.feature.chat.presentation.permissions.PermissionsManagerFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformChatPresentationDiModule = module {
  singleOf(::MobilePermissionsManagerFactory) bind PermissionsManagerFactory::class
}