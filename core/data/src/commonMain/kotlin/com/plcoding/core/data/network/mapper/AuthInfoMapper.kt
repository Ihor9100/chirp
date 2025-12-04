package com.plcoding.core.data.network.mapper

import com.plcoding.core.data.network.model.AuthInfoAm
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.core.domain.model.AuthInfo

class AuthInfoMapper(
  private val userMapper: UserMapper,
) : Mapper<AuthInfoAm, AuthInfo, Unit> {

  override fun map(from: AuthInfoAm, params: Unit): AuthInfo {
    return with(from) {
      AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = userMapper.map(user, Unit)
      )
    }
  }
}
