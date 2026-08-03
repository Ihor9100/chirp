package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmProfileImageDto(
  val publicUrl: String,
)
