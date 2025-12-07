package com.plcoding.core.data.repository.remote

import com.plcoding.core.data.mapper.AuthInfoMapper
import com.plcoding.core.data.model.AuthInfoAm
import com.plcoding.core.data.model.LoginRequestAm
import com.plcoding.core.data.model.RegisterRequestAm
import com.plcoding.core.data.model.ResendVerificationEmailRequestAm
import com.plcoding.core.data.tools.get
import com.plcoding.core.data.tools.post
import com.plcoding.core.domain.model.AuthInfo
import com.plcoding.core.domain.repository.remote.AuthRemoteRepository
import com.plcoding.core.domain.result.DataError
import com.plcoding.core.domain.result.Empty
import com.plcoding.core.domain.result.Result
import com.plcoding.core.domain.result.map
import io.ktor.client.HttpClient

class AuthRemoteDataRepository(
  private val httpClient: HttpClient,
  private val authInfoMapper: AuthInfoMapper,
) : AuthRemoteRepository {

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