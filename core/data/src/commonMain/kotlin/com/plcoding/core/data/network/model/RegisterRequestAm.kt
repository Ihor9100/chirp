package com.plcoding.core.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestAm(
  val username: String,
  val email: String,
  val password: String,
)
