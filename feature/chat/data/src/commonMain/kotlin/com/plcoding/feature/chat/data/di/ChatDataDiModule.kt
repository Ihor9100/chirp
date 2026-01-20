package com.plcoding.feature.chat.data.di

import com.plcoding.feature.chat.data.mapper.ChatMapper
import com.plcoding.feature.chat.data.mapper.ChatMemberMapper
import com.plcoding.feature.chat.data.mapper.ChatMessageMapper
import com.plcoding.feature.chat.data.repository.remote.ChatRemoteDataRepository
import com.plcoding.feature.chat.domain.repository.remote.ChatRemoteRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDataDiModule = module {
  singleOf(::ChatRemoteDataRepository) bind ChatRemoteRepository::class

  factoryOf(::ChatMapper)
  factoryOf(::ChatMemberMapper)
  factoryOf(::ChatMessageMapper)
}
