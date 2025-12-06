package com.plcoding.core.data.network.mapper

import com.plcoding.core.data.network.model.UserAm
import com.plcoding.core.domain.mapper.BiMapper
import com.plcoding.core.domain.model.User

class UserMapper : BiMapper<UserAm, User, Unit> {

  override fun map(from: UserAm, params: Unit): User {
    return with(from) {
      User(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl,
      )
    }
  }

  override fun reverse(
    from: User,
    params: Unit
  ): UserAm {
    return with(from) {
      UserAm(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl,
      )
    }
  }
}
