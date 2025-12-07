package com.plcoding.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestAm(
  val email: String,
  val password: String,
)
