package com.plcoding.feature.chat.domain.repository

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import kotlinx.coroutines.flow.Flow

interface DeviceTokenRepository {

  val token: Flow<String?>

  suspend fun registerToken(token: String, platform: String): Empty<DataError.Remote>
  suspend fun unregisterToken(token: String): Empty<DataError.Remote>
}