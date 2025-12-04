package com.plcoding.core.domain.network.service

import com.plcoding.core.domain.error.DataError
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.utils.Empty
import com.plcoding.core.domain.utils.Result

interface AuthService {

  suspend fun login(
    email: String,
    password: String,
  ): Result<AuthInfo, DataError.Remote>

  suspend fun register(
    username: String,
    email: String,
    password: String,
  ): Empty<DataError.Remote>

  suspend fun resendVerificationEmail(
    email: String,
  ): Empty<DataError.Remote>

  suspend fun verifyEmail(
    token: String,
  ): Empty<DataError.Remote>
}