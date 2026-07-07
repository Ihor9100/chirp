package com.plcoding.feature.chat.domain.observer

import kotlinx.coroutines.flow.Flow

interface AppConnectivityObserver {
  val isConnected: Flow<Boolean>
}
