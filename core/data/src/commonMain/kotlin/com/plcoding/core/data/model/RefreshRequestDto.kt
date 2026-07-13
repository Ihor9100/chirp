package com.plcoding.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDto(
  val refreshToken: String,
)
