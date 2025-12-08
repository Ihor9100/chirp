package com.plcoding.core.data.tools

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDataStore(getPath: () -> String): DataStore<Preferences> {
  return PreferenceDataStoreFactory.createWithPath {
    getPath().toPath()
  }
}

fun getDataStoreFileName(prefix: String): String {
  return "$prefix.preferences_pb"
}
