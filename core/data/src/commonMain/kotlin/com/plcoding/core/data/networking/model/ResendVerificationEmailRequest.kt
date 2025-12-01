package com.plcoding.core.data.networking.model

import kotlinx.serialization.Serializable

@Serializable
data class ResendVerificationEmailRequest(
  val email: String,
)
