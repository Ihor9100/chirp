package com.plcoding.feature.chat.database

import androidx.room.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
expect object ChirpDatabaseConstructor : RoomDatabaseConstructor<ChirpDatabase> {
  override fun initialize(): ChirpDatabase
}
