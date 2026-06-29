package com.plcoding.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.plcoding.core.data.mapper.AuthInfoMapper
import com.plcoding.core.data.model.AuthInfoAm
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class PreferencesDataRepository(
  private val json: Json,
  private val authInfoMapper: AuthInfoMapper,
  private val dataStore: DataStore<Preferences>,
) : PreferencesRepository {

  private val authInfoKey = stringPreferencesKey("auth_info_key")
  private val dataKey = stringPreferencesKey("data_key")

  override fun observeAuthInfo(): Flow<AuthInfo?> {
    return dataStore.data.map {
      val serialized = it[authInfoKey]
      serialized?.run {
        val authInfoAm = json.decodeFromString<AuthInfoAm>(this)
        authInfoMapper.map(authInfoAm)
      }
    }
  }

  override suspend fun saveAuthInfo(authInfo: AuthInfo?) {
    if (authInfo == null) {
      dataStore.edit { it.remove(authInfoKey) }
      return
    }

    dataStore.edit {
      val authInfoAm = authInfoMapper.reverse(authInfo)
      it[authInfoKey] = json.encodeToString(authInfoAm)
    }
  }

  override suspend fun saveData(data: String) {
    dataStore.edit {
      it[dataKey] = data
    }
  }
}