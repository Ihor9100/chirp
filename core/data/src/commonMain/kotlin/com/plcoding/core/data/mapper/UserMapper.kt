package com.plcoding.core.data.mapper

import com.plcoding.core.data.model.UserAm
import com.plcoding.core.domain.mapper.BiMapper
import com.plcoding.core.domain.model.User

class UserMapper : BiMapper<UserAm, User> {

  override fun map(from: UserAm): User {
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

  override fun reverse(from: User): UserAm {
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
