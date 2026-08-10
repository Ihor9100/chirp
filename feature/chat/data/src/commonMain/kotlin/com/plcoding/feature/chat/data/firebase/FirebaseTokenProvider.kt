package com.plcoding.feature.chat.data.firebase

import kotlinx.coroutines.flow.Flow

expect class FirebaseTokenProvider {
  val token: Flow<String?>
}