package com.plcoding.feature.chat.database

import androidx.room.RoomDatabase

expect class ChirpDatabaseBuilderFactory {
  fun create(): RoomDatabase.Builder<ChirpDatabase>
}
