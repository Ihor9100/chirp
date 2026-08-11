package com.plcoding.core.data.repository

import com.plcoding.core.data.mapper.toDomain
import com.plcoding.core.data.model.AuthInfoDto
import com.plcoding.core.data.model.EmailRequestDto
import com.plcoding.core.data.model.LoginRequestDto
import com.plcoding.core.data.model.RefreshTokenDto
import com.plcoding.core.data.model.RegisterRequestDto
import com.plcoding.core.data.model.ResendVerificationEmailRequestDto
import com.plcoding.core.data.model.ResetPasswordRequestDto
import com.plcoding.core.data.tools.get
import com.plcoding.core.data.tools.post
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.AuthRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.map
import com.plcoding.core.domain.result.onSuccess
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.request.post

class AuthDataRepository(
  private val httpClient: HttpClient,
) : AuthRepository {

  override suspend fun login(
    email: String,
    password: String
  ): Result<AuthInfo, DataError.Remote> {
    return httpClient.post<LoginRequestDto, AuthInfoDto>(
      route = "/auth/login",
      request = LoginRequestDto(
        email = email,
        password = password,
      )
    ).map { it.toDomain() }
  }

  override suspend fun logout(refreshToken: String): Empty<DataError.Remote> {
    return httpClient.post<RefreshTokenDto, Unit>(
      route = "/auth/logout",
      request = RefreshTokenDto(refreshToken)
    ).onSuccess {
      httpClient.authProvider<BearerAuthProvider>()?.clearToken()
    }
  }

  override suspend fun forgotPassword(email: String): Empty<DataError.Remote> {
    return httpClient.post(
      route = "/auth/forgot-password",
      request = EmailRequestDto(email)
    )
  }

  override suspend fun resetPassword(
    password: String,
    token: String
  ): Empty<DataError.Remote> {
    return httpClient.post(
      route = "/auth/reset-password",
      request = ResetPasswordRequestDto(password, token),
    )
  }

  override suspend fun register(
    username: String,
    email: String,
    password: String
  ): Empty<DataError.Remote> {
    return httpClient.post<RegisterRequestDto, Unit>(
      route = "/auth/register",
      request = RegisterRequestDto(username, email, password),
    )
  }

  override suspend fun resendVerificationEmail(email: String): Empty<DataError.Remote> {
    return httpClient.post(
      route = "/auth/resend-verification",
      request = ResendVerificationEmailRequestDto(email)
    )
  }

  override suspend fun verifyEmail(token: String): Empty<DataError.Remote> {
    return httpClient.get(
      route = "/auth/verify",
      params = mapOf("token" to token),
    )
  }
}