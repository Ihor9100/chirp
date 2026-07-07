package com.plcoding.feature.chat.domain.observer

import kotlinx.coroutines.flow.Flow

interface AppLifecycleObserver {
  val isInForeground: Flow<Boolean>
}
