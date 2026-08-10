package com.plcoding.feature.chat.domain.repository

import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import kotlinx.coroutines.flow.Flow

interface FirebaseTokenRepository {

  val token: Flow<String?>

  suspend fun registerFirebaseToken(token: String, platform: String): Empty<DataError.Remote>
  suspend fun unregisterFirebaseToken(token: String): Empty<DataError.Remote>
}