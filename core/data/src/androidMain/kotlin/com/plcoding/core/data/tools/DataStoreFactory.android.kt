package com.plcoding.core.data.tools

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStore(context: Context): DataStore<Preferences> {
  return createDataStore {
    context.filesDir.resolve(getDataStoreFileName(context.packageName)).absolutePath
  }
}
