package com.plcoding.core.data.mapper

import com.plcoding.core.data.model.AuthInfoAm
import com.plcoding.core.domain.model.AuthInfo

fun AuthInfoAm.toDomain(): AuthInfo = AuthInfo(
  accessToken = accessToken,
  refreshToken = refreshToken,
  user = user.toDomain(),
)

fun AuthInfo.toAm(): AuthInfoAm = AuthInfoAm(
  accessToken = accessToken,
  refreshToken = refreshToken,
  user = user.toAm(),
)
