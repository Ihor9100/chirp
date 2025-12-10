package com.plcoding.core.domain.repository.local

import com.plcoding.core.domain.model.AuthInfo
import kotlinx.coroutines.flow.Flow

interface PreferencesLocalRepository {

  fun observeAuthInfo(): Flow<AuthInfo?>
  suspend fun saveAuthInfo(authInfo: AuthInfo?)

  suspend fun saveData(data: String)
}
