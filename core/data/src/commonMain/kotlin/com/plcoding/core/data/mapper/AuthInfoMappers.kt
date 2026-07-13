package com.plcoding.core.data.mapper

import com.plcoding.core.data.model.AuthInfoDto
import com.plcoding.core.domain.model.AuthInfo

fun AuthInfoDto.toDomain(): AuthInfo = AuthInfo(
  accessToken = accessToken,
  refreshToken = refreshToken,
  user = user.toDomain(),
)

fun AuthInfo.toDto(): AuthInfoDto = AuthInfoDto(
  accessToken = accessToken,
  refreshToken = refreshToken,
  user = user.toDto(),
)
