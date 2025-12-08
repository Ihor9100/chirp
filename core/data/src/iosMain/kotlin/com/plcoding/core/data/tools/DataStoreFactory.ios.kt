@file:OptIn(ExperimentalForeignApi::class)

package com.plcoding.core.data.tools

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun createDataStore(): DataStore<Preferences> {
  return createDataStore {
    val directory = NSFileManager.defaultManager.URLForDirectory(
      directory = NSDocumentDirectory,
      inDomain = NSUserDomainMask,
      appropriateForURL = null,
      create = false,
      error = null
    )
    val prefix = getDataStoreFileName(NSBundle.mainBundle.bundleIdentifier.orEmpty())
    requireNotNull(directory).path + getDataStoreFileName(prefix)
  }
}
