package com.plcoding.feature.chat.domain.handler

import com.plcoding.chat.domain.models.ConnectionState

interface ConnectionErrorHandler {
  fun getConnectionState(throwable: Throwable): ConnectionState
  fun transformException(throwable: Throwable): Throwable
  fun isRetriableError(throwable: Throwable): Boolean
}