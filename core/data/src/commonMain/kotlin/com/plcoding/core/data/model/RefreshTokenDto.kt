package com.plcoding.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDto(
  val refreshToken: String,
)
