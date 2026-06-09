package com.plcoding.core.domain.repository

import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result

interface AuthRepository {

  suspend fun login(
    email: String,
    password: String,
  ): Result<AuthInfo, DataError.Remote>

  suspend fun forgotPassword(
    email: String,
  ): Empty<DataError.Remote>

  suspend fun resetPassword(
    password: String,
    token: String,
  ): Empty<DataError.Remote>

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