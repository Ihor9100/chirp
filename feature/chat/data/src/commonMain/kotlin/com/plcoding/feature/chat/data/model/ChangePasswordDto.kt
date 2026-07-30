package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordDto(
  val oldPassword: String,
  val newPassword: String,
)
