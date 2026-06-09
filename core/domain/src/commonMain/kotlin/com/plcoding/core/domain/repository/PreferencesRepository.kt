package com.plcoding.core.domain.repository

import com.plcoding.core.domain.model.AuthInfo
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

  fun observeAuthInfo(): Flow<AuthInfo?>
  suspend fun saveAuthInfo(authInfo: AuthInfo?)

  suspend fun saveData(data: String)
}