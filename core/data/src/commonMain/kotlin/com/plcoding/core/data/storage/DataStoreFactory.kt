package com.plcoding.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDataStore(getPath: () -> String): DataStore<Preferences> {
  return PreferenceDataStoreFactory.createWithPath {
    getPath().toPath()
  }
}

const val DATA_STORE_FILE_NAME = "data_store_file_pb"
