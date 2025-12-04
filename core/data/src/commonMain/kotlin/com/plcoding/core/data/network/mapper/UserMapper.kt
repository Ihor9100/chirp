package com.plcoding.core.data.network.mapper

import com.plcoding.core.data.network.model.UserAm
import com.plcoding.core.domain.mapper.Mapper
import com.plcoding.core.domain.model.User

class UserMapper : Mapper<UserAm, User, Unit> {

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
}
