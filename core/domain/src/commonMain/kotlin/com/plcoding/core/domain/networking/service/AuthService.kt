package com.plcoding.core.domain.networking.service

import com.plcoding.core.domain.Result
import com.plcoding.core.domain.utils.DataError

interface AuthService {

  suspend fun register(
    username: String,
    email: String,
    password: String,
  ): Result<Unit, DataError.Remote>

  suspend fun resendVerificationEmail(
    email: String,
  ): Result<Unit, DataError.Remote>
}