package com.plcoding.feature.chat.domain.network

import com.plcoding.feature.chat.domain.model.ConnectionState

interface ConnectionErrorHandler {
  fun getConnectionState(throwable: Throwable): ConnectionState
  fun transformException(throwable: Throwable): Throwable
  fun isRetriableError(throwable: Throwable): Boolean
}