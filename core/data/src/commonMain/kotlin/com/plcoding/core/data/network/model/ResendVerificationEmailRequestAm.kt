package com.plcoding.core.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ResendVerificationEmailRequestAm(
  val email: String,
)
