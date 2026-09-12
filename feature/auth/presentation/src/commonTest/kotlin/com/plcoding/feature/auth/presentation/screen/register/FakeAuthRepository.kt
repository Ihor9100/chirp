package com.plcoding.feature.auth.presentation.screen.register

import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.AuthRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result

class FakeAuthRepository: AuthRepository {

  var registerCallCount = 0
  var lastUsername: String? = null
  var lastEmail: String? = null
  var lastPassword: String? = null
  var registerResult: Result<Unit, DataError.Remote>? = null

  override suspend fun login(
    email: String,
    password: String
  ): Result<AuthInfo, DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun logout(refreshToken: String): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun forgotPassword(email: String): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun resetPassword(
    password: String,
    token: String
  ): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun register(
    username: String,
    email: String,
    password: String
  ): Empty<DataError.Remote> {
    registerCallCount += 1
    lastUsername = username
    lastEmail = email
    lastPassword = password
    return registerResult!!
  }

  override suspend fun resendVerificationEmail(email: String): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }

  override suspend fun verifyEmail(token: String): Empty<DataError.Remote> {
    TODO("Not yet implemented")
  }
}