package com.plcoding.core.domain.storage

import com.plcoding.core.domain.model.AuthInfo
import kotlinx.coroutines.flow.Flow

interface SessionStorage {
  fun observeAuthInfo(): Flow<AuthInfo>
  suspend fun saveAuthInfo(authInfo: AuthInfo)
}
