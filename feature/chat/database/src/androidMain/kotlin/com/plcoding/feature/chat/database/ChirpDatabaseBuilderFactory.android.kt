package com.plcoding.feature.chat.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class ChirpDatabaseBuilderFactory(
  private val context: Context,
) {

  actual fun create(): RoomDatabase.Builder<ChirpDatabase> {
    return Room.databaseBuilder(
      context = context.applicationContext,
      name = context.getDatabasePath(ChirpDatabase.NAME).absolutePath,
    )
  }
}