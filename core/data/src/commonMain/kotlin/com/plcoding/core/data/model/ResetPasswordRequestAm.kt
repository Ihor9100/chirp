package com.plcoding.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequestAm(
  val newPassword: String,
  val token: String,
)
