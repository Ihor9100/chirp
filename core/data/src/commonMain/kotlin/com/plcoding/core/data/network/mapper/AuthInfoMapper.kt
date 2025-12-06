package com.plcoding.core.data.network.mapper

import com.plcoding.core.data.network.model.AuthInfoAm
import com.plcoding.core.domain.mapper.BiMapper
import com.plcoding.core.domain.model.AuthInfo

class AuthInfoMapper(
  private val userMapper: UserMapper,
) : BiMapper<AuthInfoAm, AuthInfo, Unit> {

  override fun map(from: AuthInfoAm, params: Unit): AuthInfo {
    return with(from) {
      AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = userMapper.map(user, Unit)
      )
    }
  }

  override fun reverse(
    from: AuthInfo,
    params: Unit
  ): AuthInfoAm {
    return with(from) {
      AuthInfoAm(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = userMapper.reverse(user, Unit)
      )
    }
  }
}
