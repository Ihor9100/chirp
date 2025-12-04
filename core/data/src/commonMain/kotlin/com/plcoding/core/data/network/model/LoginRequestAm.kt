package com.plcoding.core.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestAm(
  val email: String,
  val password: String,
)
