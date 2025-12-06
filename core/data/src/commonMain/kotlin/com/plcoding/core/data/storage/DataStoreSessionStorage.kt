package com.plcoding.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.plcoding.core.data.network.mapper.AuthInfoMapper
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.storage.SessionStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class DataStoreSessionStorage(
  private val json: Json,
  private val authInfoMapper: AuthInfoMapper,
  private val dataStore: DataStore<Preferences>,
) : SessionStorage {

  private val authInfoKey = stringPreferencesKey("auth_info_key")

  override fun observeAuthInfo(): Flow<AuthInfo?> {
    return dataStore.data.map {
      val serialized = it[authInfoKey]
      serialized?.run { json.decodeFromString(this) }
    }
  }

  override suspend fun saveAuthInfo(authInfo: AuthInfo?) {
    if (authInfo == null) {
      dataStore.edit { it.remove(authInfoKey) }
      return
    }

    dataStore.edit {
      val authInfoAm = authInfoMapper.reverse(authInfo, Unit)
      it[authInfoKey] = json.encodeToString(authInfoAm)
    }
  }
}