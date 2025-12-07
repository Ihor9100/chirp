package com.plcoding.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResendVerificationEmailRequestAm(
  val email: String,
)
