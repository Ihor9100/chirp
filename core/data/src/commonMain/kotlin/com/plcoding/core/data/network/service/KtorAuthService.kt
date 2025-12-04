package com.plcoding.core.data.network.service

import com.plcoding.core.data.network.get
import com.plcoding.core.data.network.mapper.AuthInfoMapper
import com.plcoding.core.data.network.model.AuthInfoAm
import com.plcoding.core.data.network.model.LoginRequestAm
import com.plcoding.core.data.network.model.RegisterRequestAm
import com.plcoding.core.data.network.model.ResendVerificationEmailRequestAm
import com.plcoding.core.data.network.post
import com.plcoding.core.domain.error.DataError
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.network.service.AuthService
import com.plcoding.core.domain.utils.Empty
import com.plcoding.core.domain.utils.Result
import com.plcoding.core.domain.utils.map
import io.ktor.client.HttpClient

class KtorAuthService(
  private val httpClient: HttpClient,
  private val authInfoMapper: AuthInfoMapper,
) : AuthService {

  override suspend fun login(
    email: String,
    password: String
  ): Result<AuthInfo, DataError.Remote> {
    return httpClient.post<LoginRequestAm, AuthInfoAm>(
      route = "/auth/login",
      request = LoginRequestAm(
        email = email,
        password = password,
      )
    ).map {
      authInfoMapper.map(it, Unit)
    }
  }

  override suspend fun register(
    username: String,
    email: String,
    password: String
  ): Empty<DataError.Remote> {
    return httpClient.post<RegisterRequestAm, Unit>(
      route = "/auth/register",
      request = RegisterRequestAm(username, email, password),
    )
  }

  override suspend fun resendVerificationEmail(email: String): Empty<DataError.Remote> {
    return httpClient.post(
      route = "/auth/resend-verification",
      request = ResendVerificationEmailRequestAm(email)
    )
  }

  override suspend fun verifyEmail(token: String): Empty<DataError.Remote> {
    return httpClient.get(
      route = "/auth/verify",
      params = mapOf("token" to token),
    )
  }
}