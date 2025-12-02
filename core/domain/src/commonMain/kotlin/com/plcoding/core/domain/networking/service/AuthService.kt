package com.plcoding.core.domain.networking.service

import com.plcoding.core.domain.Empty
import com.plcoding.core.domain.utils.DataError

interface AuthService {

  suspend fun register(
    username: String,
    email: String,
    password: String,
  ): Empty<DataError.Remote>

  suspend fun resendVerificationEmail(
    email: String,
  ): Empty<DataError.Remote>
}