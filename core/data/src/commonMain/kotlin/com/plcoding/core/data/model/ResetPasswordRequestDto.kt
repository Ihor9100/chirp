package com.plcoding.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequestDto(
  val newPassword: String,
  val token: String,
)
