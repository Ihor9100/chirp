package com.plcoding.core.data.mapper

import com.plcoding.core.data.model.UserDto
import com.plcoding.core.domain.model.User

fun UserDto.toDomain(): User = User(
  id = id,
  email = email,
  username = username,
  hasVerifiedEmail = hasVerifiedEmail,
  profilePictureUrl = profilePictureUrl,
)

fun User.toDto(): UserDto = UserDto(
  id = id,
  email = email,
  username = username,
  hasVerifiedEmail = hasVerifiedEmail,
  profilePictureUrl = profilePictureUrl,
)
