package com.plcoding.core.data.mapper

import com.plcoding.core.data.model.UserAm
import com.plcoding.core.domain.model.User

fun UserAm.toDomain(): User = User(
  id = id,
  email = email,
  username = username,
  hasVerifiedEmail = hasVerifiedEmail,
  profilePictureUrl = profilePictureUrl,
)

fun User.toAm(): UserAm = UserAm(
  id = id,
  email = email,
  username = username,
  hasVerifiedEmail = hasVerifiedEmail,
  profilePictureUrl = profilePictureUrl,
)
