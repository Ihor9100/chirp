package com.plcoding.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestAm(
  val username: String,
  val email: String,
  val password: String,
)
