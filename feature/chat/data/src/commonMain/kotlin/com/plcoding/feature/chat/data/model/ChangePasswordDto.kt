package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordDto(
  val currentPassword: String,
  val newPassword: String,
)
