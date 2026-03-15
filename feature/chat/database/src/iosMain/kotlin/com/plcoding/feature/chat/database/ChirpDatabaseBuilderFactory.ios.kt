package com.plcoding.feature.chat.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class ChirpDatabaseBuilderFactory {

  actual fun create(): RoomDatabase.Builder<ChirpDatabase> {
    return Room.databaseBuilder(
      name = getDatabaseDirectory() + "/${ChirpDatabase.NAME}",
    )
  }

  @OptIn(ExperimentalForeignApi::class)
  private fun getDatabaseDirectory(): String {
    return NSFileManager.defaultManager.URLForDirectory(
      directory = NSDocumentDirectory,
      inDomain = NSUserDomainMask,
      appropriateForURL = null,
      create = false,
      error = null
    )?.path!!
  }
}