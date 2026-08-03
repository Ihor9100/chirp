package com.plcoding.feature.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileImageUploadDto(
  val uploadUrl: String,
  val publicUrl: String,
  val headers: Map<String, String>,
)
