package com.plcoding.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoDto(
  val accessToken: String,
  val refreshToken: String,
  val user: UserDto,
)
