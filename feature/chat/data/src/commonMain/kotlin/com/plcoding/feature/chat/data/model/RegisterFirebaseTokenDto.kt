package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterFirebaseTokenDto(
  val token: String,
  val platform: String,
)
