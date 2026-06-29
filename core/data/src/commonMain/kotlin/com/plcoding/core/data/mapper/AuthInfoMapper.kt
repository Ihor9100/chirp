package com.plcoding.core.data.mapper

import com.plcoding.core.data.model.AuthInfoAm
import com.plcoding.core.domain.mapper.BiMapper
import com.plcoding.core.domain.model.AuthInfo

class AuthInfoMapper(
  private val userMapper: UserMapper,
) : BiMapper<AuthInfoAm, AuthInfo> {

  override fun map(from: AuthInfoAm): AuthInfo {
    return with(from) {
      AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = userMapper.map(user)
      )
    }
  }

  override fun reverse(from: AuthInfo): AuthInfoAm {
    return with(from) {
      AuthInfoAm(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = userMapper.reverse(user)
      )
    }
  }
}
